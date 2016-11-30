package com.imie.unicorn.controller;

/**
 * Created by Yornletard on 30/11/2016.
 */
public class Track {

    String artist;
    String song;
    String url;

    public Track(String artist, String song, String url){
        this.artist = artist;
        this.song = song;
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
