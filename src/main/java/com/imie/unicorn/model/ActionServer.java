package com.imie.unicorn.model;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yornletard on 15/12/2016.
 */
public interface ActionServer {

        void addPlayer(Player player);

        HashMap<String, Player> getListPlayers();

        public void sendToAll(Message message) throws IOException;

        void deconnexion(ThreadServer threadServer);

    public void sendToAllWithReset(Message message) throws IOException;
}
