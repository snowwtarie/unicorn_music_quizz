package com.imie.unicorn.controller;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class Player {

    private String id;
    private String ip;
    private String name;
    private String surname;
    private int score;
    private Boolean isReady;

    public Player(String ip, String name, String surname, int score, Boolean isReady){
        this.id = name.concat(ip);
        this.ip = ip;
        this.name = name;
        this.surname = surname;
        this.score = score;
        this.isReady = isReady;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
}
