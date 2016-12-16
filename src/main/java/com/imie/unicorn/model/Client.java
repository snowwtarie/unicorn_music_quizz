package com.imie.unicorn.model;


import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.PlayerMp3;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.view.JFenetre;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class Client {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;

    public PlayerMp3 getPlayerMp3() {
        return playerMp3;
    }

    public void setPlayerMp3(PlayerMp3 playerMp3) {
        this.playerMp3 = playerMp3;
    }

    private PlayerMp3 playerMp3;
    private Track currentTrack;

    public Client() throws IOException {
        //Socket socketClient = new Socket("192.168.43.43", 5000);
        Socket socketClient = new Socket("127.0.0.1", 5000);

        out = new ObjectOutputStream(socketClient.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socketClient.getInputStream());
    }


    public void run() throws IOException, ClassNotFoundException {
        while (true) {
            Message message = (Message) in.readObject();
            traiterMessage(message);
            if (message.getKey().equals("Deconnexion")){
                break;
            }
        }
    }

    public void traiterMessage(Message message) throws IOException {
        if (message.getKey().equals("Connexion")) {
            System.out.println("Client >> Connexion");
            sendMessage(new Message("List_Players", null));
        } else if (message.getKey().equals("refreshListPlayer")) {
            System.out.println(((HashMap<String, Player>) message.getValue()).size());
            JFenetre.getInstance().refreshReadyPlayers((HashMap<String, Player>) message.getValue());
            System.out.println("Client >> Refresh Player List");
        } else if (message.getKey().equals("PlayerReady")) {
            System.out.println("Client >> Player Ready");
            JFenetre.getInstance().refreshReadyPlayers((HashMap<String, Player>) message.getValue());
        } else if (message.getKey().equals("GameStart")) {
            JFenetre.getInstance().switchtoGame((Track) message.getValue());
            this.currentTrack = (Track) message.getValue();
            playerMp3 = new PlayerMp3((Track) message.getValue());
            playerMp3.start();
            System.out.println(currentTrack.getArtist()+" "+currentTrack.getTitle());
        } else if (message.getKey().equals("Perdu")) {
            JFenetre.getInstance().getPanelBorder().getPanelCardMain().getPanelMainPlay().getWrongProp().setText("FAUX");
            JFenetre.getInstance().getPanelBorder().getPanelCardMain().getPanelMainInfoTrack().getMessage().setText("Personne n'a gagne");
        } else if (message.getKey().equals("roundWinner")) {
            Player winner =(Player) message.getValue();
            JFenetre.getInstance().getPanelBorder().getPanelCardMain().getPanelMainInfoTrack().getMessage().setText("Le Gagnant est : "+winner.getPseudo());
            JFenetre.getInstance().trackFinish(currentTrack);
            JFenetre.getInstance().getClient().getPlayerMp3().kill();
        } else if (message.getKey().equals("Deconnexion")){
            out.close();
            in.close();
        }
    }

    public HashMap<String, Player> sendMessage(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
        return null;
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        JFenetre.getInstance().setClient(client);
        JFenetre.getInstance().init();
        client.run();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player p) {
       this.player = p;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }
}
