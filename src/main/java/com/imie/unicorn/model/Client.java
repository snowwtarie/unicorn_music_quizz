package com.imie.unicorn.model;


import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.view.JFenetre;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class Client {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;

    public Client() throws IOException {
        Socket socketClient = new Socket("10.4.1.14", 5000);
        //Socket socketClient = new Socket("127.0.0.1", 5000);

        out = new ObjectOutputStream(socketClient.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socketClient.getInputStream());
    }


    public void run() throws IOException, ClassNotFoundException {
        while (true) {
            Message message = (Message) in.readObject();
            traiterMessage(message);
        }
    }

    public void traiterMessage(Message message) throws IOException {
        if (message.getKey().equals("Connexion")) {
            sendMessage(new Message("List_Players", null));
        } else if (message.getKey().equals("refreshListPlayer")) {
            JFenetre.getInstance().refreshReadyPlayers((HashMap<String, Player>) message.getValue());
            System.out.println("Client >> Player List");
        } else if (message.getKey().equals("PlayerReady")) {
            System.out.println("Client >> Player Ready");
            JFenetre.getInstance().refreshReadyPlayers((HashMap<String, Player>) message.getValue());
        } else if (message.getKey().equals("GameStart")) {
            JFenetre.getInstance().switchtoGame((Track) message.getValue());
        }
    }

    public HashMap<String, Player> sendMessage(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
        return null;
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        JFenetre.getInstance().setClient(client);
        JFenetre.getInstance().init();
        client.run();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player p) {
       this.player = p;
    }
}
