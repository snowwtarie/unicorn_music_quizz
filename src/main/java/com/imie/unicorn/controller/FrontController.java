package com.imie.unicorn.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

public class FrontController {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        try {
            //TEST
            UnicornCore unicornCore = UnicornCore.getUnicornCore();

            unicornCore.addPlayer(new Player("192.168.1.1", "mat", 0, true));
            unicornCore.addPlayer(new Player("192.168.1.2", "mar", 0, true));

            unicornCore.startWait();
            while(unicornCore.isCoreReady){
                unicornCore.startTrack();
                unicornCore.checkProposition("Hold On", "mat192.168.1.1");
            };


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
