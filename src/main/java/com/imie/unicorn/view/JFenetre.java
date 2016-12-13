package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Stibo on 30/11/2016.
 * Fenetre Principale
 */
public class JFenetre extends JFrame implements KeyListener {
    private static JFenetre instance = new JFenetre();
    public static Font robotoFont;
    public static Font unicornFont;
    private static PanelBorder panelBorder;
    private static PanelFinal panelFinal;

    private JFenetre(){
        this.setTitle ("Unicorn Music Quizzz");
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setSize(new DimensionUIResource(1210, 800));
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setLocationRelativeTo ( null ) ;
        this.setResizable(false);
        this.addKeyListener(this);

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
            panelBorder = new PanelBorder();
            JFenetre.instance.setContentPane(panelBorder);
            JFenetre.instance.setVisible(true);
        }
    }

    public static JFenetre getInstance(){
        return JFenetre.instance;
    }

    public static PanelBorder getPanelBorder() {
        return panelBorder;
    }

    public void launchUI(){
        panelBorder = new PanelBorder();
        JFenetre.instance.setContentPane(panelBorder);
        JFenetre.instance.setVisible(true);
        PanelBorder.getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "wait");
        PanelBorder.getPanelCardSide().cardLayoutSide.show(PanelBorder.getPanelCardSide(), "ready");
    }

    public void refreshReadyPlayers(){
        PanelBorder.getPanelCardSide().getPanelSideReady().refreshPlayers();
    }

    public void refreshScore(){
        PanelBorder.getPanelCardSide().getPanelSideScore().refreshScore();
    }

    public void switchtoGame(){
        PanelBorder.getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "gameMain");
        PanelBorder.getPanelCardMain().getPanelMainPlay().startThread();
        PanelBorder.getPanelCardSide().cardLayoutSide.show(PanelBorder.getPanelCardSide(), "score");
    }

    public void trackFinish(){
        PanelBorder.getPanelCardMain().getPanelMainPlay().stopThread();
        PanelBorder.getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "infosTrack");
    }

    public void gameFinish(){
        panelFinal = new PanelFinal();
        JFenetre.instance.setContentPane(panelFinal);
        JFenetre.instance.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            switchtoGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
