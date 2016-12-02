package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;
import com.imie.unicorn.controller.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Stibo on 30/11/2016.
 * Contenu par le PanelCardSide / Panel Joueur-Score
 */
public class PanelSideScore extends JPanel{
    private String ipLocale;

    public PanelSideScore(){
        HashMap<String, Player> joueurs = (HashMap<String, Player>) Client.getClient().getRequest(new Message("InitOtherPlayer", null)).getValue();
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Pseudo"));
        this.add(new JLabel("Score"));

        for(Map.Entry<String, Player> p : joueurs.entrySet()) {
            this.add(createJoueurLabel(p.getValue().getPseudo()));
            this.add(new JLabel(String.valueOf(p.getValue().getScore())));
        }
    }

    private JLabel createJoueurLabel(String pseudo){
        JLabel joueur = new JLabel(pseudo);
        joueur.setPreferredSize(new Dimension(130,50));
        return joueur;
    }

}
