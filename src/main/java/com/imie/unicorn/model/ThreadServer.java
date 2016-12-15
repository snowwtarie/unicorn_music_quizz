package com.imie.unicorn.model;


import com.imie.unicorn.controller.Player;
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

    public ThreadServer(ActionServer actionServer, Socket socketClient) throws IOException {
        this.actionServer = actionServer;
        this.out = new ObjectOutputStream(socketClient.getOutputStream());
        this.in = new ObjectInputStream(socketClient.getInputStream());
    }


    public void run() {
        while (true) {
            try {
                /*if (in.read() == -1) {
                    actionServer.deconnexion(this);
                    break;
                }*/
                Message message = (Message) in.readObject();
                traiterMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void traiterMessage(Message message) throws IOException {
        if (message.getKey().equals("Connexion")) {
            actionServer.addPlayer((Player) message.getValue());
            this.sendMessage(new Message("Connexion", null));
        } else if (message.getKey().equals("List_Players")) {
            System.out.println(actionServer.getListPlayers().size());
            actionServer.sendToAll(new Message("refreshListPlayer", actionServer.getListPlayers()));
            System.out.println("Thread >>> LISTPLAYER");

        } else if (message.getKey().equals("PlayerReady")) {
            Player player = (Player) message.getValue();
            System.out.println(message.getValue().toString());
            UnicornCore.getUnicornCore().getPlayerList().get(player.getIdPlayer()).setIsReady(true);
            System.out.println("Ready");
            System.out.println("Server : checking if all players ready...");
            if (UnicornCore.getUnicornCore().checkIfAllReady()){
                System.out.println("PLAYER ARE ALL READY");
               /// send(new Message("GameStart", UnicornCore.getUnicornCore().getCurrentUrlTrack()), sc);
            }
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

    public static void main(String[] args) throws Exception {

    }
}
