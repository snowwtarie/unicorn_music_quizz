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

    public PanelSideScore(){
        initPanelSideScore();
    }

    private void initPanelSideScore(){
        HashMap<String, Player> joueurs = (HashMap<String, Player>) Client.getClient().getRequest(new Message("InitOtherPlayer", null)).getValue();
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setLayout(new GridLayout(0,2));
        this.setBackground(Color.WHITE);

        JLabel pseudo = new JLabel("Pseudo");
        pseudo.setFont(JFenetre.robotoFont.deriveFont(20f));
        pseudo.setForeground(Color.RED);

        JLabel score = new JLabel("Score", SwingConstants.CENTER);
        score.setFont(JFenetre.robotoFont.deriveFont(20f));
        score.setForeground(Color.RED);

        this.add(pseudo);
        this.add(score);

        for(Map.Entry<String, Player> p : joueurs.entrySet()) {
            this.add(createJoueurLabel(p.getValue().getPseudo()));
            JLabel playerScore = new JLabel(String.valueOf(p.getValue().getScore()), SwingConstants.CENTER);
            playerScore.setFont(JFenetre.robotoFont.deriveFont(20f));
            this.add(playerScore);
        }
    }

    private JLabel createJoueurLabel(String pseudo){
        JLabel joueur = new JLabel(pseudo);
        joueur.setFont(JFenetre.robotoFont.deriveFont(20f));
        joueur.setPreferredSize(new Dimension(130,50));
        return joueur;
    }

    public void refreshScore(){
        this.removeAll();
        initPanelSideScore();
        this.repaint();
        this.revalidate();
    }

}
