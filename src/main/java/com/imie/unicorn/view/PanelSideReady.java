package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Stibo on 30/11/2016.
 * Contenu par le PanelCardSide / Panel Ready to play ?
 */
public class PanelSideReady extends JPanel implements ActionListener {
    private JCheckBox readyBox = new JCheckBox("Ready ?");


    public PanelSideReady(){
        ArrayList<String> joueurs = (ArrayList<String>) Client.getClient().getRequest(new Message("InitOtherPlayer", null)).getValue();

        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Pseudo"));
        this.add(new JLabel("Pret ?"));
        this.add(new JLabel("You"));
        this.add(readyBox);
        readyBox.addActionListener(this);


        int i;
        for (i=0; i<joueurs.size(); i++){
            this.add(createJoueurLabel(joueurs.get(i).toString()));
            this.add(new JLabel("v"));
        }
        this.setPreferredSize(new DimensionUIResource(300, 800));
        this.setVisible(true);
    }

    private JLabel createJoueurLabel(String pseudo){
        JLabel joueur = new JLabel(pseudo);
        return joueur;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (readyBox.isSelected()){
            Client.getClient().getRequest(new Message("Ready", null)).getValue();
            System.out.println("Send Ready");
        } else {
            System.out.println("Not Ready");
        }
    }
}
