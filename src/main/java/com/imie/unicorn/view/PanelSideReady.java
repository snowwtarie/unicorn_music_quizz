package com.imie.unicorn.view;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.util.*;

/**
 * Created by Stibo on 30/11/2016.
 * Contenu par le PanelCardSide / Panel Ready to play ?
 */
public class PanelSideReady extends JPanel {
    public PanelSideReady(){
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Pseudo"));
        this.add(new JLabel("Pret ?"));
    }

    public PanelSideReady(java.util.List j){
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Pseudo"));
        this.add(new JLabel("Pret ?"));
        this.add(new JLabel("You"));
        this.add(new JCheckBox());

        int i;
        for (i=0; i<j.size(); i++){
            this.add(createJoueurLabel(j.get(i).toString()));
            this.add(new JLabel("v"));
        }
        this.setPreferredSize(new DimensionUIResource(300, 800));
        this.setVisible(true);
    }

    private JLabel createJoueurLabel(String pseudo){
        JLabel joueur = new JLabel(pseudo);
        return joueur;
    }

}
