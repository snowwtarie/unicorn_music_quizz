package com.imie.unicorn.controller;

import java.io.IOException;

/**
 * Created by Yornletard on 01/12/2016.
 */
public class GameTimer extends Thread {

    private int time;

    public GameTimer(int time){
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
            System.out.println("Timer de " + time/1000 + " secondes...");
            UnicornCore.getUnicornCore().setIsReady(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
