package com.imie.unicorn.controller;

import com.imie.unicorn.model.ServerCentral;

import java.io.IOException;

/**
 * Created by Yornletard on 01/12/2016.
 */
public class GameTimer extends Thread {

    private int time;
    private ServerCentral serverCentral;

    public GameTimer(int time, ServerCentral serverCentral){
        this.time = time;
        this.serverCentral = serverCentral;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
            System.out.println("Timer de " + time/1000 + " secondes...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            UnicornCore.getUnicornCore().nextTrack();
            serverCentral.launchNextSong(UnicornCore.getUnicornCore().getCurrentTrack());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
