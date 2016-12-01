package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;

/**
 * Created by Stibo on 30/11/2016.
 * Fenetre Principale
 */
public class JFenetre extends JFrame {
    private static JFenetre instance = new JFenetre();

    private JFenetre(){
        this.setTitle ("Unicorn Music Quizzz");
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setSize(new DimensionUIResource(1210, 800));
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setLocationRelativeTo ( null ) ;
        this.setResizable(false);
    }

    public void init() {

    }

    public static void main(String args []) {
        getInstance();
        JOptionPane jOptionPane = new JOptionPane();
        String pseudo = jOptionPane.showInputDialog(null, "Veuillez saisir votre pseudo", "Unicorn Pseudo", JOptionPane.QUESTION_MESSAGE);
        boolean connexion = (Boolean) Client.getClient().getRequest(new Message("Connexion", pseudo)).getValue();
        if (connexion) {
            JFenetre.instance.setContentPane(new PanelBorder());
            JFenetre.instance.setVisible(true);
        }
    }

    public static JFenetre getInstance(){
        return JFenetre.instance;
    }

}
