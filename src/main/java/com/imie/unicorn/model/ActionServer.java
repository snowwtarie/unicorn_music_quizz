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

        Track waitForNextSong() throws InterruptedException;
}
