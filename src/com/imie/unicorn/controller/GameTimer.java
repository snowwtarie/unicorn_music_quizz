package com.imie.unicorn.controller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yornletard on 01/12/2016.
 */
public class GameTimer extends Thread {

    int time;
    public GameTimer(int time){
        this.time = time;
    }

    @Override
    public void run() {
        System.out.println("Timer lancé...");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
