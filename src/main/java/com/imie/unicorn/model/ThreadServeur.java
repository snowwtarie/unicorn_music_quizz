package com.imie.unicorn.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ThreadServeur extends Thread {

    private ServerSocket serveur;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServeurInterface serveurInterface;

    public ThreadServeur(ServerSocket serveur, ServeurInterface serveurInterface, Socket client) throws IOException {
        super();
        this.serveur = serveur;
        this.serveurInterface = serveurInterface;
        this.oos = new ObjectOutputStream(client.getOutputStream());
        this.oos.flush();
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {

        while (true) {
            try {
                if (!this.getOis().readUTF().equals("")) {
                    serveurInterface.envoyerMessage(this, this.getOis().readUTF());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void contacterClient(String msg) throws IOException {
        this.getOos().writeUTF(msg);
        this.getOos().flush();
    }

    public ServerSocket getServeur() {
        return this.serveur;
    }

    public ObjectOutputStream getOos() {
        return this.oos;
    }

    public ObjectInputStream getOis() {
        return this.ois;
    }
}
