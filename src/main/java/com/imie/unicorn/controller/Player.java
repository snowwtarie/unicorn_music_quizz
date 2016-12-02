package com.imie.unicorn.controller;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class Player {

    private String idPlayer;
    private String ip;
    private String pseudo;
    private int score;
    private Boolean isReady;

    public Player(String ip, String pseudo, int score, Boolean isReady){
        this.idPlayer = pseudo.concat(ip);
        this.ip = ip;
        this.pseudo = pseudo;
        this.score = score;
        this.isReady = isReady;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean ready) {
        isReady = ready;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + idPlayer +
                ", ip='" + ip + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", score='" + score + '\'' +
                ", isReady='" + isReady + '\'' +
                '}';
    }
}