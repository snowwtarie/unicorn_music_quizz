package com.imie.unicorn.controller;

import java.net.MalformedURLException;

public class FrontController {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        UnicornCore unicornCore = new UnicornCore();
        unicornCore.addPlayer(new Player("192.168.1.1", "Hamza", "a", 0, true));
        unicornCore.addPlayer(new Player("192.168.1.2", "Marius", "g", 0, true));
        unicornCore.addPlayer(new Player("192.168.1.3", "Thibault", "r", 0, true));
        unicornCore.addPlayer(new Player("192.168.1.4", "Mathieu", "p", 0, true));

        Thread.sleep(4000);
        unicornCore.checkProposition("Daft Punk", "Hamza192.168.1.1");

    }

}
