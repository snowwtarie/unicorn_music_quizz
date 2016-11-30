package com.imie.unicorn.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ThreadServeur extends Thread {

    private Socket client;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServeurInterface serveurInterface;

    public ThreadServeur(Socket client, ServeurInterface serveurInterface) throws IOException {
        super();
        this.client = client;
        this.serveurInterface = serveurInterface;
        this.oos = new ObjectOutputStream(this.client.getOutputStream());
        this.oos.flush();
        this.ois = new ObjectInputStream(this.client.getInputStream());
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

    public Socket getClient() {
        return this.client;
    }

    public ObjectOutputStream getOos() {
        return this.oos;
    }

    public ObjectInputStream getOis() {
        return this.ois;
    }
}
