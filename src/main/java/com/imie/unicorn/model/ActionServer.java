package com.imie.unicorn.model;

import com.imie.unicorn.controller.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yornletard on 15/12/2016.
 */
public interface ActionServer {

        public void addPlayer(Player player);

        public HashMap<String, Player> getListPlayers();

}
