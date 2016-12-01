package com.imie.unicorn.controller;

import java.io.IOException;
import java.net.MalformedURLException;

public class FrontController {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        try {
            UnicornCore unicornCore = new UnicornCore();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
