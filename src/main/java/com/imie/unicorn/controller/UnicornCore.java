package com.imie.unicorn.controller;

import java.io.IOException;
import java.util.*;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class UnicornCore {

    private static UnicornCore unicornCore;
    private HashMap<String, Player> playerList;
    private ArrayList<Track> listTrack;
    private Track currentTrack;
    private static final int idPlaylist = 908622995;
    public boolean isCoreReady;
    private GameTimer trackTimer;

    private UnicornCore() throws IOException {
        this.playerList = new HashMap<String, Player>();
        this.getAllTrack(idPlaylist);
    }

    public static UnicornCore getUnicornCore() throws IOException {
        if (unicornCore == null) {
            unicornCore = new UnicornCore();
        }
        return unicornCore;
    }

    public void startWait(){
        isCoreReady = false;
        GameTimer smallTimer = new GameTimer(5000);
        smallTimer.start();
    }

    public void startTrack() throws IOException, InterruptedException {

        this.nextTrack();

        isCoreReady = true;
        trackTimer = new GameTimer(30000);
        trackTimer.start();
        trackTimer.join();
    }

    public boolean isReady() {
        return isCoreReady;
    }

    public void setIsReady(boolean state) {
        this.isCoreReady = state;
    }

    private void nextTrack() {
        int index = listTrack.indexOf(currentTrack);
        currentTrack = listTrack.get(((index > listTrack.size()) ? index : -1) + 1);
    }

    private void getAllTrack(int idPlaylist) throws IOException {
        DeezerAPI deezerAPI = new DeezerAPI(idPlaylist);
        this.listTrack = deezerAPI.getListTrack();
    }

    public boolean checkIfAllReady(){
        if (playerList.size() > 1) {
            for(Map.Entry<String, Player> p : this.playerList.entrySet()) {
                if (p.getValue().getIsReady())
                    return false;
            }
            return true;
        }
        return false;
    }

    public void checkProposition(String proposition, String idPlayer){
        Boolean checkSong = proposition.equals(currentTrack.getTitle());
        Boolean checkArtist = proposition.equals(currentTrack.getArtist());

        if(checkSong || checkArtist){
            int actualScore = playerList.get(idPlayer).getScore();
            playerList.get(idPlayer).setScore(actualScore + 1);
            this.trackTimer.interrupt();
        }
        this.nextTrack();
    }

    public String getCurrentUrlTrack(){
        return currentTrack.getPreview();
    }

    public HashMap<String, Player> getPlayerList() {
        return playerList;
    }

    public void addPlayer(Player player){
        this.playerList.put(player.getId(), player);
    }
    public void removePlayer(Player player){
        this.playerList.remove(player);
    }

    /*public void setPlayerList(HashMap<String, Player> playerList) {
        this.playerList = playerList;
    }*/
}
