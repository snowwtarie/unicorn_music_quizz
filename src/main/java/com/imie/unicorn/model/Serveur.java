package com.imie.unicorn.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


public class Serveur implements ServeurInterface {

    private static ServerSocket serveur;
    private static List<ThreadServeur> threadServeurList = new ArrayList<ThreadServeur>();
    private static int nbThreads = 0;
    private static Serveur instance = new Serveur();

    private Serveur() {
        try {
            Serveur.serveur = new ServerSocket(9002);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Serveur getInstance() {
        return Serveur.instance;
    }

    public ServerSocket getServeur() {
        return Serveur.serveur;
    }

    public static void main(String[] args) {
        Serveur serveur = Serveur.getInstance();

        while (true) {
            try {
                Serveur.threadServeurList.add(new ThreadServeur(serveur.getServeur().accept(), (ServeurInterface) serveur));
                Serveur.threadServeurList.get(Serveur.nbThreads).start();
                Serveur.nbThreads++;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
    }

    public void envoyerMessage(ThreadServeur origine, String msg) throws IOException {
        for (ThreadServeur ts : threadServeurList) {
            if (!ts.equals(origine)) {
                ts.contacterClient(msg);
            }
        }
    }
}
