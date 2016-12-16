package com.imie.unicorn.model;


import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.controller.UnicornCore;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadServer extends  Thread {

    private ActionServer actionServer;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;

    public ThreadServer(ActionServer actionServer, Socket socketClient) throws IOException {
        this.actionServer = actionServer;
        this.out = new ObjectOutputStream(socketClient.getOutputStream());
        this.in = new ObjectInputStream(socketClient.getInputStream());
    }


    public void run() {
        while (true) {
            try {
                Message message = (Message) in.readObject();
                traiterMessage(message);

                if (message.getKey().equals("Deconnexion")){
                    break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void traiterMessage(Message message) throws IOException {
        if (message.getKey().equals("Connexion")) {
            this.player = (Player) message.getValue();
            actionServer.addPlayer((Player) message.getValue());
            this.sendMessage(new Message("Connexion", null));
        } else if (message.getKey().equals("List_Players")) {
            System.out.println(actionServer.getListPlayers().size());
            actionServer.sendToAllWithReset(new Message("refreshListPlayer", actionServer.getListPlayers()));
            System.out.println("Thread >>> LISTPLAYER");

        } else if (message.getKey().equals("PlayerReady")) {
            Player player = (Player) message.getValue();
            System.out.println(message.getValue().toString());
            UnicornCore.getUnicornCore().getPlayerList().get(player.getIdPlayer()).setIsReady(true);
            System.out.println("Ready");
            System.out.println("Server : checking if all players ready...");
            actionServer.sendToAllWithReset(new Message("refreshListPlayer", actionServer.getListPlayers()));
            if (UnicornCore.getUnicornCore().checkIfAllReady()){
                System.out.println("PLAYER ARE ALL READY");
                Track track = actionServer.getCurrentTrack();
                actionServer.sendToAllWithReset(new Message("GameStart", track));
            }
        } else if (message.getKey().equals("noWinner")) {
            try {
                actionServer.waitForNextSong();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (message.getKey().equals("Proposition")){
            actionServer.checkProposition((String) message.getValue(), this);

        } else if (message.getKey().equals("Deconnexion")){
            sendMessage(new Message("Deconnexion", null));
            out.close();
            in.close();
            actionServer.removePlayer(this);
        }
    }


    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public void sendMessageWithReset(Message message) throws IOException {
        out.reset();
        sendMessage(message);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public static void main(String[] args) throws Exception {

    }
}
