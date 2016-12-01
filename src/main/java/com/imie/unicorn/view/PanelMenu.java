package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Stibo on 1/12/2016.
 */
public class PanelMenu extends JPanel implements ActionListener {
    public PanelMenu(){
        JButton startbutton = new JButton("Start");
        JLabel label = new JLabel("Unicorn Music Quizz <3");
        this.add(label);
        this.add(startbutton);
        startbutton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean connexion = (Boolean) Client.getClient().getRequest(new Message("Connexion", null)).getValue();
        if(connexion){
            JFenetre.connexionServer();
        }
    }
}
