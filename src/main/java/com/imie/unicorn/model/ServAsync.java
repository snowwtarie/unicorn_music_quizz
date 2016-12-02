package com.imie.unicorn.model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by marius on 01/12/16.
 */
public class ServAsync implements Runnable {

    public final static String ADDRESS = "127.0.0.1";
    public final static int PORT = 8511;
    public final static long TIMEOUT = 10000;

    private ServerSocketChannel serverChannel;
    private Selector selector;

    private Map<SocketChannel, byte[]> dataTracking = new HashMap<SocketChannel, byte[]>();

    public static void main(String[] args) {
        ServAsync serveur = new ServAsync();
        Thread thread = new Thread(serveur);
        thread.start();
    }

    public ServAsync() {
        init();
    }

    private void init() {
        System.out.println("Initializing server...");

        if (selector != null)
            return;
        if (serverChannel != null)
            return;

        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(ADDRESS, PORT));

            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Prêt à accepter des connexions...");

        try {
            while (!Thread.currentThread().isInterrupted()) {
                selector.select(TIMEOUT);

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        System.out.println("Connexion acceptée !");
                        accept(key);
                    }

                    if (key.isWritable()) {
                        System.out.println("En écriture...");
                        write(key);
                    }

                    if (key.isReadable()) {
                        System.out.println("En lecture...");
                        read(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        byte[] data = dataTracking.get(channel);
        dataTracking.remove(channel);
        channel.write(ByteBuffer.wrap(data));
        key.interestOps(SelectionKey.OP_READ);
    }

    private void closeConnection() {
        System.out.println("Fermeture du serveur...");
        if (selector != null) {
            try {
                selector.close();
                serverChannel.socket().close();
                serverChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_WRITE);
        byte[] hello = new String("Hello from server!").getBytes();
        dataTracking.put(socketChannel, hello);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        readBuffer.clear();
        int read;
        try {
            read = channel.read(readBuffer);
        } catch (IOException e) {
            System.out.println("Erreur de lecture, fin de la connexion !");
            key.cancel();
            channel.close();
            return;
        }

        if (read == -1) {
            System.out.println("Lecture vide, fermeture de la connexion !");
            channel.close();
            key.cancel();
            return;
        }

        readBuffer.flip();
        byte[] data = new byte[1000];
        readBuffer.get(data, 0, read);
        System.out.println("Received : " + new String(data));

        echo(key, data);
    }

    private void echo(SelectionKey key, byte[] data) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        dataTracking.put(socketChannel, data);
        key.interestOps(SelectionKey.OP_WRITE);
    }
 }
