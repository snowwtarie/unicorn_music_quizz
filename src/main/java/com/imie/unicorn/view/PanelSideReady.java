package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;
import com.imie.unicorn.controller.Player;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by Stibo on 30/11/2016.
 * Contenu par le PanelCardSide / Panel Ready to play ?
 */
public class PanelSideReady extends JPanel implements ActionListener {
    private JCheckBox readyBox = new JCheckBox("Ready ?");
    private String ipLocale;

    public PanelSideReady(){
        HashMap<String, Player> joueurs = (HashMap<String, Player>) Client.getClient().getRequest(new Message("InitOtherPlayer", null)).getValue();
        try {
            ipLocale = InetAddress.getLocalHost ().getHostAddress ();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.setLayout(new GridLayout(0,2));
        this.add(new JLabel("Pseudo"));
        this.add(new JLabel("Pret ?"));
        for(Map.Entry<String, Player> p : joueurs.entrySet()) {
            if(p.getValue().getIp().equals(ipLocale)){
                this.add(new JLabel("You"));
                this.add(readyBox);
                readyBox.addActionListener(this);
            } else {
                this.add(createJoueurLabel(p.getValue().getPseudo()));
                if(p.getValue().getIsReady()){
                    this.add(new JLabel("v"));
                } else {
                    this.add(new JLabel("x"));
                }
            }
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
