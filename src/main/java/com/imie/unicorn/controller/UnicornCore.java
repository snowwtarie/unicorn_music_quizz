package com.imie.unicorn.controller;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class UnicornCore {

    private static UnicornCore unicornCore;
    private HashMap<String, Player> playerList;
    private ArrayList<Track> listTrack;
    private Track currentTrack;
    private static final long idPlaylist = 2511342804l;
    public boolean isCoreReady;
    private GameTimer trackTimer;
    private Score score;

    private UnicornCore() throws IOException {
        System.out.println("UnicornCore : UnicornCore starting...");
        this.playerList = new HashMap<String, Player>();
        this.getAllTrack(idPlaylist);
        this.score = Score.getScore();
    }

    public static UnicornCore getUnicornCore() throws IOException {
        if (unicornCore == null) {
            unicornCore = new UnicornCore();
        }
        System.out.println();
        return unicornCore;
    }

//    public void startTrack() throws IOException, InterruptedException {
//
//        this.nextTrack();
//
//        isCoreReady = true;
//        trackTimer = new GameTimer(30000);
//        trackTimer.start();
//        trackTimer.join();
//    }

    public boolean isReady() {
        return isCoreReady;
    }

    public void setIsReady(boolean state) {
        this.isCoreReady = state;
    }

    public void nextTrack() {
        int index = listTrack.indexOf(currentTrack);
        currentTrack = listTrack.get(((index < listTrack.size()) ? index : -1) + 1);
    }

    private void getAllTrack(long idPlaylist) throws IOException {
        System.out.println("UnicornCore : getting track list by deezer API...");
        DeezerAPI deezerAPI = new DeezerAPI(idPlaylist);
        this.listTrack = deezerAPI.getListTrack();
        this.currentTrack = listTrack.get(1);
    }

    public boolean checkIfAllReady(){
        if (playerList.size() >= 1) {
            for(Map.Entry<String, Player> p : this.playerList.entrySet()) {
                if (!p.getValue().getIsReady())
                    return false;
            }
            return true;
        }
        return false;
    }


    public Boolean handleProposition(String proposition, String idPlayer){
        if(!checkProposition("artist", proposition)){
            if(!checkProposition("title", proposition)){
                System.out.println("perdu");
                return false;
            }
        }
        else {
                System.out.println("Gagné ! + 1 point ! t'es un gagnant Serge...");
                int actualScore = playerList.get(idPlayer).getScore();
                playerList.get(idPlayer).setScore(actualScore + 1);
                return true;
            }
        return false;
    }

    private boolean checkProposition(String artisteOrTitle, String proposition){
        String response;

        if(artisteOrTitle == "artist"){
            response = currentTrack.getArtist();
            //response = "Big Brown Eyes";
        }
        else{
            response = currentTrack.getTitle();
        }

        System.out.println("réponse proposée : " + proposition + " , (réponse : " + currentTrack.getArtist() + " - " + currentTrack.getTitle());

        String[] reponseArray = response.toUpperCase().replaceAll("[^a-zA-Z0-9]+"," ").split(" ");

        String[] propositionArray = proposition.toUpperCase().replaceAll("[^a-zA-Z0-9]+"," ").split(" ");

        int count = 0;

        for(String word: reponseArray){
            for(int i=0; i < propositionArray.length; i++){
                String r = propositionArray[i].toString();
                if(word.contains(r)){
                    System.out.println("mot : " + r + " present dans la réponse");
                    count += 1;
                }
            }
        }

        if(count == reponseArray.length)
            return true;

        return false;
    }

    public String getCurrentUrlTrack(){
        return currentTrack.getPreview();
    }
    public Track getCurrentTrack(){
        return currentTrack;
    }

    public HashMap<String, Player> getPlayerList() {
        return playerList;
    }

    public void addPlayer(Player player){
        this.playerList.put(player.getIdPlayer(), player);
    }
    public void removePlayer(Player player){
        this.playerList.remove(player.getIdPlayer());
    }

    public void closeGame(){
        for(Map.Entry<String, Player> p : this.playerList.entrySet()){
            score.addPlayerScore(p.getValue().getIdPlayer(), p.getValue().getIp(), p.getValue().getPseudo(), p.getValue().getScore());
        }
    }

    /*public void setPlayerList(HashMap<String, Player> playerList) {
        this.playerList = playerList;
    }*/
}
