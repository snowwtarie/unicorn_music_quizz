package com.imie.unicorn.controller;

public class Track {
    private int id;
    private String preview;
    private String title;
    private String artist;

    public Track(int id, String preview, String title, String artist) {
        this.id = id;
        this.preview = preview;
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", preview='" + preview + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
