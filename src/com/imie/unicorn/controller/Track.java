package com.imie.unicorn.controller;

public class Track {
    private int id;
    private String preview;
    private String title;
    private String artist;

    public Track(String preview, String title, String artist) {
        this.preview = preview;
        this.title = title;
        this.artist = artist;
    }

    public Track(int id, String preview, String title, String artist) {
        this.id = id;
        this.preview = preview;
        this.title = title;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
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
