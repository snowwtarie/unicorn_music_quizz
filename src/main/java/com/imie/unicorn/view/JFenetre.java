package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Stibo on 30/11/2016.
 * Fenetre Principale
 */
public class JFenetre extends JFrame {
    private static JFenetre instance = new JFenetre();
    public static Font robotoFont;
    public static Font unicornFont;

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

        try {
            //create the font to use. Specify the size!
            robotoFont = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir")+"/src/main/res/Roboto-Regular.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir")+"/src/main/res/Roboto-Regular.ttf")));

        } catch (IOException e) {
            robotoFont = new Font("Serif", Font.PLAIN, 14);
            e.printStackTrace();
        } catch(FontFormatException e) {
            robotoFont = new Font("Serif", Font.PLAIN, 14);
            e.printStackTrace();
        }

        try {
            //create the font to use. Specify the size!
            unicornFont = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir")+"/src/main/res/LostinWild.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir")+"/src/main/res/LostinWild.ttf")));

        } catch (IOException e) {
            unicornFont = new Font("Serif", Font.PLAIN, 14);
            e.printStackTrace();
        } catch(FontFormatException e) {
            unicornFont = new Font("Serif", Font.PLAIN, 14);
            e.printStackTrace();
        }


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
