package com.imie.unicorn.model;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.PlayerMp3;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.view.JFenetre;
import com.imie.unicorn.view.Message;
import jaco.mp3.player.MP3Player;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Scanner;


public class Client {

    private ByteBuffer buffer;
    private Selector selector;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel sc = null;
    private SelectionKey clientKey;
    private Player player;
    private HashMap<String, Player> listeJoueurs;
    private Track currentTrack;
    private PlayerMp3 playerMp3 = null;

    public Client() throws IOException, ClassNotFoundException {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress("10.4.1.14", 14659);
        sc = SocketChannel.open(isa);

        sc.configureBlocking(false);
        clientKey = sc.register(selector, SelectionKey.OP_READ);
    }

    public void init() throws IOException, ClassNotFoundException {
        while (selector.select() > 0) {
            for (SelectionKey key : selector.selectedKeys()) {
                selector.selectedKeys().remove(key);

                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    this.buffer = ByteBuffer.allocate(1024);
                    traiterMessage(read(sc, this.buffer));
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    public void traiterMessage(Message message) throws IOException {
        if (message.getKey().equals("Connexion")) {
            System.out.println("CONNNEXION");
            this.getPlayerList();
        } else if (message.getKey().equals("List_Players")) {
            JFenetre.getInstance().refreshReadyPlayers((HashMap<String, Player>) message.getValue());
        } else if (message.getKey().equals("PlayerReady")) {
            JFenetre.getInstance().refreshReadyPlayers((HashMap<String, Player>) message.getValue());
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

    private Message sendMessage(Message message) throws IOException {
        this.send(message, sc);
        return null;
    }
    //Methode qui sera utilisée en reception par le client
    private Message receiveMessage(Message message){
        return null;
    }

    public void getConnection(String pseudo) throws IOException {
        Message message = new Message("Connexion", new Player(InetAddress.getLocalHost().getHostAddress(), pseudo, 0, false));
        send(message, this.sc);
    }

    public void getPlayerList() throws IOException {
        Message message = new Message("List_Players", null);
        send(message, this.sc);
    }

    public HashMap<String, Player> playerList() throws IOException {
        return new HashMap<String, Player>();
    }

    public void playerReady() throws IOException {
        Message message = new Message("PlayerReady", player);
        send(message, this.sc);
    }

    public Track getCurrentTrack(){
        return this.currentTrack;
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        JFenetre.getInstance().setClient(client);
        JFenetre.getInstance().init();
        client.init();
    }

    private void searchServer(){
        try{
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] sendData = "connectionRequest".getBytes();

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
