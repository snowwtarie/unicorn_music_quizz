package com.imie.unicorn.model;


import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.UnicornCore;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerCentral extends Thread implements ActionServer{

    private UnicornCore core;
    private final ServerSocket socketServer;
    private ArrayList<ThreadServer> threadServers;

    public ServerCentral() throws IOException {
        core = UnicornCore.getUnicornCore();
        threadServers = new ArrayList<>();
        socketServer = new ServerSocket(5000);
    }


    public void run() {
        while (true) {
            try {
                Socket socketClient = socketServer.accept();

                ThreadServer ts = new ThreadServer((ActionServer) this, socketClient);
                ts.start();

                threadServers.add(ts);
            } catch (Exception e) {}
        }
    }


    public static void main(String[] args) throws Exception {
        new ServerCentral().run();
    }


    @Override
    public void addPlayer(Player player) {
        core.addPlayer(player);
    }

    @Override
    public HashMap<String, Player> getListPlayers() {
        return core.getPlayerList();
    }

    @Override
    public void sendToAll(Message message) throws IOException {
        for(ThreadServer t : threadServers){
            t.sendMessage(message);
        }
    }
}
