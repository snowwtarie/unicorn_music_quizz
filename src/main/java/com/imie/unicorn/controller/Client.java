package com.imie.unicorn.controller;


import com.imie.unicorn.view.JFenetre;
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
            HashMap<String, Player> test = new HashMap<String, Player>();
            test.put("Hamza192.168.0.1", new Player("192.168.0.1", "Hamza", 0, false));
            test.put("Matt192.168.0.2", new Player("192.168.0.2", "Matt", 0, false));
            test.put("Marius192.168.0.3", new Player("192.168.0.3", "Marius", 0, true));
            test.put("Stibo10.4.1.2", new Player("192.168.43.43", "Stibo", 0, true));

            return new Message("InitOtherPlayer", test);
        }else if(message.getKey().equals("Connexion")){
            return new Message("Connexion", true);
        }else if(message.getKey().equals("Ready")){
            return new Message("Ready", true);
        }else if(message.getKey().equals("EndTrack")){
            Track currentTrack = new Track(130890802, "http://cdn-preview-0.deezer.com/stream/09b0344f45be0621b3b8fb4ed34d767f-3.mp3", "Hold On", "Møme", "http://e-cdn-images.deezer.com/images/cover/e6c9680601a592273beed1db72b37ea4/250x250-000000-80-0-0.jpg");
            return new Message("EndTrack", currentTrack);
        }else if(message.getKey().equals("Winner")){
            Player winner = new Player("192.168.0.1", "Hamza", 0, false);
            return new Message("Winner", winner);
        }else if (message.getKey().equals("gameWinner")){
            Player winner = new Player("192.168.0.1", "Hamza", 0, false);
            return new Message("gameWinner", winner);
        }else if (message.getKey().equals("refreshPlayers")){
            JFenetre.getInstance().refreshReadyPlayers();
            return new Message("refreshPlayers", null);
        }else if (message.getKey().equals("InitPlayer")){
            HashMap<String, Player> test = new HashMap<String, Player>();
            test.put("Hamza192.168.0.1", new Player("192.168.0.1", "Hamza", 0, true));
            test.put("Matt192.168.0.2", new Player("192.168.0.2", "Matt", 0, true));
            test.put("Marius192.168.0.3", new Player("192.168.0.3", "Marius", 0, true));
            test.put("Stibo10.4.1.2", new Player("192.168.43.43", "Stibo", 0, true));

            return new Message("InitPlayer", test);
        }else if (message.getKey().equals("Song")){
            return new Message("Song", false);
        }else if (message.getKey().equals("songNumber")){
            return new Message("songNumber", 2);
        }
            return null;
        }

}
