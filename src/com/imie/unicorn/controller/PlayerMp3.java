package com.imie.unicorn.controller;

import jaco.mp3.player.MP3Player;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class PlayerMp3 extends Thread{

    MP3Player player;
    Track currentTrack;

    @Override
    public void run(){
        while (true) {

        }
    }

    public PlayerMp3(Track track) throws MalformedURLException {
        this.currentTrack = track;
        this.player = new MP3Player();
        player.addToPlayList(new URL(track.getUrl()));
    }

    public void play() throws InterruptedException {
        player.play();
    }

    public void pause(){
        player.pause();
    }

    public void nextSong(){
        player.skipForward();
    }

    public void previousSong(){
        player.skipBackward();
    }

    public MP3Player getPlayer() {
        return player;
    }

    public void setPlayer(MP3Player player) {
        this.player = player;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }
}
