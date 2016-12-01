package com.imie.unicorn.controller;

import java.io.IOException;
import java.util.*;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class UnicornCore {

    private HashMap<String, Player> playerList;
    private List<Track> listTrack;
    private Track currentTrack;
    private static final int idPlaylist = 908622995;
    private boolean nextTrack;

    public UnicornCore() throws IOException {
        this.playerList = new HashMap<String, Player>();
        this.startGame();
    }

    private void startGame() throws IOException {

        this.getListTrack(idPlaylist);

        while(!this.checkIfAllReady()) {
        }

        for(Track track: listTrack){
            this.currentTrack = track;
            GameTimer gameTimer = new GameTimer(2000);

            System.out.println("piste suivante");
        }
    }
    private void getListTrack(int idPlaylist) throws IOException {
        DeezerAPI deezerAPI = new DeezerAPI(idPlaylist);
        this.listTrack = deezerAPI.getListTrack();
    }

    private boolean checkIfAllReady(){
        if (playerList.size() < 1) {
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
            this.nextTrack = true;
        }
        this.nextTrack = false;
    }

    public String getCurrentUrlTrack(){
        return currentTrack.getPreview();
    }

    public HashMap<String, Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(HashMap<String, Player> playerList) {
        this.playerList = playerList;
    }
}
