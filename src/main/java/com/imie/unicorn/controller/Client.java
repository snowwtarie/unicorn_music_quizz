package com.imie.unicorn.controller;


import com.imie.unicorn.view.Message;

import java.util.ArrayList;

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

            ArrayList<String> test = new ArrayList<String>();
            test.add("Marius");
            test.add("Amzha");
            test.add("Mathieu");
            return new Message("InitOtherPlayer", test);
        }else if(message.getKey().equals("Ready")){

        }

        return null;
    }

}
