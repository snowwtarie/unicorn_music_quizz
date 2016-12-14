package com.imie.unicorn.model;

import com.imie.unicorn.view.Message;

import java.io.*;
import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.UnicornCore;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;


public class Server {

    private Selector selector;
    private Charset charset =  Charset.forName("UTF-8");
    private UnicornCore core;
    private ServerSocketChannel server;

    public void init() throws Exception {
        this.core = UnicornCore.getUnicornCore();

        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress isa = new InetSocketAddress("10.4.1.14", 14659);

        server.socket().bind(isa);
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {

            for (SelectionKey key : selector.selectedKeys()) {
                selector.selectedKeys().remove(key);

                if (key.isAcceptable()) {
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    key.interestOps(SelectionKey.OP_ACCEPT);
                }

                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    String content = "";
                    Message message = null;

                    try {
                        message = read(sc, buff);

                        System.out.println("Client << " + message.getValue());
                        key.interestOps(SelectionKey.OP_READ);
                    } catch (IOException e) {
                        key.cancel();

                        if (key.channel() == null) {
                            key.channel().close();
                        }
                    }

                    if (message != null) {
                        for (SelectionKey sk : selector.keys()) {
                            Channel channel = sk.channel();

                            if (channel instanceof SocketChannel) {
                                SocketChannel to = (SocketChannel) channel;
                                traiterMessage(message, to);
                            }
                        }
                    }
                }
            }
        }
    }

    private void traiterMessage(Message message, SocketChannel sc) throws IOException {
        if (message.getKey().equals("Connexion")) {
            UnicornCore.getUnicornCore().addPlayer((Player) message.getValue());
            System.out.println("CONNNEXION");
            send(new Message("Connexion", null), sc);
        } else if (message.getKey().equals("List_Players")) {
            send(new Message("List_Players", UnicornCore.getUnicornCore().getPlayerList()), sc);
            System.out.println("LISTPLAYER");
        } else if (message.getKey().equals("PlayerReady")) {
            UnicornCore.getUnicornCore().getPlayerList().get((Player) message.getValue()).setIsReady(true);
            send(new Message("PlayerReady", UnicornCore.getUnicornCore().getPlayerList()), sc);
            System.out.println("Ready");
        }
    }

    public Message read(SocketChannel socketChannel, ByteBuffer buffer) throws IOException, ClassNotFoundException {
        socketChannel.read(buffer);

        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
        ObjectInputStream ois = new ObjectInputStream(bais);

        return (Message) ois.readObject();
    }

    public void send(Message message, SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        oos.flush();
        socketChannel.write(ByteBuffer.wrap(baos.toByteArray()));
    }

    public static void main(String[] args) throws Exception {
        new Server().init();
    }
}
