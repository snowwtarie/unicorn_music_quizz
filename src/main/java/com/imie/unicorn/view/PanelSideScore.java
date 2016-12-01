package com.imie.unicorn.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Stibo on 30/11/2016.
 * Contenu par le PanelCardSide / Panel Joueur-Score
 */
public class PanelSideScore extends JPanel{
    public PanelSideScore(){
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Pseudo"));
        this.add(new JLabel("Score"));
    }

    public PanelSideScore(java.util.List j){
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Pseudo"));
        this.add(new JLabel("Score"));

        int i;
        for (i=0; i<j.size(); i++){
            this.add(createJoueurLabel(j.get(i).toString()));
            this.add(new JLabel("0"));
        }
    }

    private JLabel createJoueurLabel(String pseudo){
        JLabel joueur = new JLabel(pseudo);
        joueur.setPreferredSize(new Dimension(130,50));
        return joueur;
    }

}
