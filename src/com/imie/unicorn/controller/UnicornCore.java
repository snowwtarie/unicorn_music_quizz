package com.imie.unicorn.controller;

import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class UnicornCore {

    private HashMap<String, Player> playerList;
    private ArrayList<Track> trackList;
    private PlayerMp3 playerMp3;
    private Boolean nextSong;

    public UnicornCore() throws InterruptedException, MalformedURLException {
        this.playerList = new HashMap<String, Player>();
        this.playerMp3 = null;
        this.nextSong = false;
        this.startGame();
    }

    private void startGame() throws MalformedURLException, InterruptedException {
        //liste de chanson de test
        trackList.add(new Track("Daft Punk", "Harder Better Faster Stronger", "http://cdn-preview-5.deezer.com/stream/51afcde9f56a132096c0496cc95eb24b-4.mp3"));
        trackList.add(new Track("Daft Punk", "Harder Better Faster Stronger", "http://cdn-preview-5.deezer.com/stream/51afcde9f56a132096c0496cc95eb24b-4.mp3"));

        while(!this.checkIfAllReady()) {
        }


    }

    public void addPlayer(Player player){
        this.playerList.put(player.getId(), player);
    }

    public void removePlayer(Player player){
        this.playerList.remove(player);
    }

    private boolean checkIfAllReady(){
        for(Map.Entry<String, Player> p : this.playerList.entrySet()) {
            if (p.getValue().getIsReady())
                return false;
        }
        return true;
    }

    public void checkProposition(String proposition, String idPlayer){
        Boolean checkSong = proposition.equals(playerMp3.getCurrentTrack().song);
        Boolean checkArtist = proposition.equals(playerMp3.getCurrentTrack().artist);

        if(checkSong || checkArtist){
            int actualScore = playerList.get(idPlayer).getScore();
            playerList.get(idPlayer).setScore(actualScore + 1);
            this.nextSong = true;
        }
    }

}
