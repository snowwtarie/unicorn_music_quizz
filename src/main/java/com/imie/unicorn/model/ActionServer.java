package com.imie.unicorn.model;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Yornletard on 15/12/2016.
 */
public interface ActionServer {

        void addPlayer(Player player);

        HashMap<String, Player> getListPlayers();

        void sendToAll(Message message) throws IOException;


        void sendToAllWithReset(Message message) throws IOException;

        Track getCurrentTrack();

        void removePlayer(ThreadServer ts) throws IOException;

        void waitForNextSong() throws InterruptedException, IOException;

        void launchNextSong(Track track) throws InterruptedException, IOException;

        void checkProposition(String s, ThreadServer ts) throws IOException;

        void checkReady() throws IOException;
}
