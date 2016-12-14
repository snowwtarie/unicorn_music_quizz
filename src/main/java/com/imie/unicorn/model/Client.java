package com.imie.unicorn.model;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.PlayerMp3;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.view.JFenetre;
import com.imie.unicorn.view.Message;
import jaco.mp3.player.MP3Player;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Scanner;


public class Client {

    private Selector selector;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel sc = null;
    private SelectionKey clientKey;
    private JFenetre fenetre = JFenetre.getInstance();
    private PlayerMp3 playerMp3 = null;

    private void init() throws IOException {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 3000);
        sc = SocketChannel.open(isa);

        sc.configureBlocking(false);
        clientKey = sc.register(selector, SelectionKey.OP_READ);

        new ClientThread().start();

        fenetre.setClient(this);
        fenetre.init();

        Scanner scan = new Scanner(System.in);
        String msg = null;

        while (scan.hasNextLine()) {

            /**
             * TODO: - transmettre l'objet Message via ByteBuffer
             */
            msg = scan.nextLine();
            Message message = new Message("String", msg);
            send(message, sc);
            //this.clientKey.attach(new Message("String", msg));
            //sc.write(ByteBuffer.wrap(msg.getBytes()));
            //sc.write(charset.encode(scan.nextLine()));
        }
    }

    private class ClientThread extends Thread {
        public void run() {
            try {
                while (selector.select() > 0) {
                    for (SelectionKey key : selector.selectedKeys()) {
                        selector.selectedKeys().remove(key);

                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer buff = ByteBuffer.allocate(1024);
                            String content = "";

                            System.out.println("Server >> " + read(sc, buff).getValue());
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Message message, SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        oos.flush();
        socketChannel.write(ByteBuffer.wrap(baos.toByteArray()));
    }

    public Message read(SocketChannel socketChannel, ByteBuffer buffer) throws IOException, ClassNotFoundException {
        socketChannel.read(buffer);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Message) ois.readObject();
    }
    private Message sendMessage(Message message){
        return null;
    }

    //Methode qui sera utilisée en reception par le client
    private Message receiveMessage(Message message){

        return null;
    }

    public boolean getConnection(String pseudo) throws IOException {
        return (Boolean) this.sendMessage(new Message("connection", new String("pseudo"))).getValue();
    }

    public HashMap<String, Player> playerList() throws IOException {
        return (HashMap<String, Player>) this.sendMessage(new Message("playerList", null)).getValue();
    }

    public void playerReady() throws IOException {
        this.sendMessage(new Message("playerReady", null));
    }

    public Track getCurrentTrack(){
        try {
            return (Track) this.sendMessage(new Message("currentTrack", null)).getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkProposition(String proposition) throws IOException {
        Boolean b = (Boolean) this.sendMessage(new Message("proposition", proposition)).getValue();
        if(b)
            playerMp3.interrupt();
        return b;
    }
    public Player getRoundWinner() throws IOException {
        return (Player) this.sendMessage(new Message("winner", null)).getValue();
    }
    public Player getGameWinner() throws IOException {
        return (Player) this.sendMessage(new Message("gameWinner", null)).getValue();
    }

    public void playTrack() throws MalformedURLException, InterruptedException {
        Track track = (Track) this.receiveMessage(new Message("playTrack", null)).getValue();
        this.playerMp3 = new PlayerMp3(track);
        this.playerMp3.play();
    }





    public static void main(String[] args) throws IOException {
        new Client().init();
    }

}
