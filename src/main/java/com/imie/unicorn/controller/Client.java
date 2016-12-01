package com.imie.unicorn.controller;


import com.imie.unicorn.view.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Client {

    private static Client client;

    private Client() {
    }

    public static Client getClient() {
       if (client == null){
           client = new Client();
       }

       return client;
    }

    public Message getRequest(Message message) {
        if (message.getKey().equals("InitOtherPlayer")) {

            HashMap<String, Object> test = new HashMap<String, Object>();

            return new Message("InitOtherPlayer", test);
        }else if(message.getKey().equals("Connexion")){
            return new Message("Connexion", true);
        }else if(message.getKey().equals("Ready")){
            return new Message("Ready", true);
        }

        return null;
    }

}
