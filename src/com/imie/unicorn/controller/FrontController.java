package com.imie.unicorn.controller;

import java.io.IOException;
import java.util.ArrayList;

public class FrontController {

    public static void main(String[] args) {
        DeezerAPI deezerAPI = new DeezerAPI(1366766145);
        try {
            ArrayList<Track> a = deezerAPI.getListTrack();
            for (Track track : a) {
                System.out.println(track);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
