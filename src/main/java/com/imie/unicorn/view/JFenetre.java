package com.imie.unicorn.view;

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
        this.add(new PanelMenu());
        this.setVisible(true);
        this.setLocationRelativeTo ( null ) ;
        this.setResizable(false);
    }

    public void init() {

    }

    public static void connexionServer(){
        JFenetre.instance.setContentPane(new PanelBorder());
        JFenetre.instance.setVisible(true);
    }

    public static void main(String args []) {
        getInstance();
    }

    public static JFenetre getInstance(){
        return JFenetre.instance;
    }

}
