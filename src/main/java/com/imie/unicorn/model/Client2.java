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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;


public class Client2 {

    /*private ByteBuffer buffer;
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
        //InetSocketAddress isa = new InetSocketAddress(this.searchServer(), 14659);
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
        player = new Player(InetAddress.getLocalHost().getHostAddress(), pseudo, 0, false);
        Message message = new Message("Connexion", player);
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

    private String searchServer() throws IOException {
        //find server using UDP broadcast
        try{
            //open random port to send the packet
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] sendData = "connectionRequest".getBytes();
            //trying to send over 255.255.255.255 broadcast address first
            try{
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                socket.send(packet);
                System.out.println("Client : looking for Server...sending discovery packet to 255.255.255.255 (default)...");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //trying to send over all network interface next
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            while(interfaces.hasMoreElements()){
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
                //no need to broadcast over loopback address or if interface is down
                if(networkInterface.isLoopback() || !networkInterface.isUp())
                    continue;
                for(InterfaceAddress interfaceAdress : networkInterface.getInterfaceAddresses() ){
                    InetAddress broadcast = interfaceAdress.getBroadcast();
                    if(broadcast == null)
                        continue;
                    //send broadcast package
                    try{
                        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        socket.send(packet);
                        System.out.println("Client : looking for Server...sending discovery packet to " + broadcast.getHostAddress() + " [Interface : " + networkInterface.getDisplayName() + "]");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //prepare packet for response
            byte[] rcvbuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(rcvbuf, rcvbuf.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData());
            if(response != null){
                System.out.println("Client : Server is responding !!! joining a game to " + response);
                return response;
            }


        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;

    }*/

}
