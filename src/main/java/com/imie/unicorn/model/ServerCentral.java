package com.imie.unicorn.model;


import com.imie.unicorn.controller.GameTimer;
import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.Track;
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
    private boolean checkDone = false;
    private boolean checkNextSong = false;

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
            } catch (Exception e) {
                break;
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new ServerCentral().run();
    }


    @Override
    public void addPlayer(Player player) {
        System.out.println("SERVER >> ADD TO PLAYER LIST :"+player);
        core.addPlayer(player);
    }

    @Override
    public HashMap<String, Player> getListPlayers() {
        return core.getPlayerList();
    }

    @Override
    public void sendToAll(Message message) throws IOException {
        System.out.println("SERVER >> SEND TO ALL");
        for(ThreadServer t : threadServers){
            t.sendMessage(message);
        }
    }

    @Override
    public void sendToAllWithReset(Message message) throws IOException {
        for(ThreadServer t : threadServers){
            try {
                t.sendMessageWithReset(message);
            } catch (Exception e){}
        }
    }

    @Override
    public Track getCurrentTrack() {
        return core.getCurrentTrack();
    }

    @Override
    public void removePlayer(ThreadServer threadServer) throws IOException {
        System.out.println("remove player");
        core.removePlayer(threadServer.getPlayer());
        threadServers.remove(threadServer);
        sendToAllWithReset(new Message("refreshListPlayer", core.getPlayerList()));
    }

    @Override
    public  void waitForNextSong() throws InterruptedException, IOException {
        new GameTimer(5000, this).start();
    }

    @Override
    public synchronized void launchNextSong(Track track) throws InterruptedException, IOException {
        if (!this.checkNextSong) {
            sendToAllWithReset(new Message("GameStart", track));
            checkNextSong = true;
        }
    }

    @Override
    public synchronized void checkProposition(String s, ThreadServer ts) throws IOException {
        if(core.handleProposition(s, ts.getPlayer().getIdPlayer())){
            this.sendToAll(new Message("roundWinner", ts.getPlayer()));
            this.checkNextSong = false;
        }

        else
            ts.sendMessage(new Message("Perdu", null));
    }

    @Override
    public synchronized void checkReady() throws IOException {
        if (UnicornCore.getUnicornCore().checkIfAllReady()){
            if (!this.checkDone) {
                System.out.println("PLAYER ARE ALL READY");
                Track track = getCurrentTrack();
                sendToAllWithReset(new Message("GameStart", track));
                checkDone = true;
            }
        }
    }
}
