package com.imie.unicorn.model;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.view.JFenetre;
import com.imie.unicorn.view.Message;

import java.io.*;
import java.net.InetSocketAddress;
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


                            /*while (sc.read(buff) > 0) {
                                sc.read(buff);
                                buff.flip();
                                content += charset.decode(buff);
                                buff.clear();
                            }*/

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

    public boolean getConnection(String pseudo){
        return (Boolean) this.sendMessage(new Message("connection", new String("pseudo"))).getValue();
    }

    public HashMap<String, Player> playerList(){
        return (HashMap<String, Player>) this.sendMessage(new Message("playerList", null)).getValue();
    }

    public void playerReady(){
        this.sendMessage(new Message("playerReady", null));
    }

    public Track getCurrentTrack(){
        return (Track) this.sendMessage(new Message("currentTrack", null)).getValue();
    }

    public boolean checkProposition(String proposition){
        return (Boolean) this.sendMessage(new Message("proposition", proposition)).getValue();
    }
    public Player getRoundWinner(){
        return (Player) this.sendMessage(new Message("winner", null)).getValue();
    }
    public Player getGameWinner(){
        return (Player) this.sendMessage(new Message("gameWinner", null)).getValue();
    }




    public static void main(String[] args) throws IOException {
        new Client().init();
    }

}
