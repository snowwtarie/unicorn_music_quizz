package com.imie.unicorn.view;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.Track;
import com.imie.unicorn.model.Client;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by Stibo on 30/11/2016.
 * Fenetre Principale
 */
public class JFenetre extends JFrame {
    private static JFenetre jFenetre;
    public static Font robotoFont;
    public static Font unicornFont;
    private static PanelBorder panelBorder;
    private static PanelFinal panelFinal;
    private Client client;

    public Client getClient() {
        return client;
    }

    private JFenetre() throws IOException {
        this.setTitle ("Unicorn Music Quizzz");
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setSize(new DimensionUIResource(1210, 800));
        this.setLocationRelativeTo ( null ) ;
        this.setResizable(false);
        this.setVisible(true);
    }

    public void init() throws IOException {

        JOptionPane jOptionPane = new JOptionPane();
        String pseudo = jOptionPane.showInputDialog(null, "Veuillez saisir votre pseudo", "Unicorn Pseudo", JOptionPane.QUESTION_MESSAGE);
        Player player = new Player (InetAddress.getLocalHost().getHostAddress(), pseudo, 0, false);
        client.setPlayer(player);
        client.sendMessage(new Message("Connexion", player ));

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

            panelBorder = new PanelBorder();
            jFenetre.setContentPane(panelBorder);
            jFenetre.setVisible(true);

    }

    public static JFenetre getInstance() throws IOException {
        if (jFenetre == null)
            jFenetre = new JFenetre();

        return jFenetre;
    }

    public static PanelBorder getPanelBorder() {
        return panelBorder;
    }

    public void launchUI(){
        //panelBorder = new PanelBorder();
        //JFenetre.instance.setContentPane(panelBorder);
        //getPanelBorder().getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "wait");
        //getPanelBorder().getPanelCardSide().getPanelSideReady().initPanelSideReady();
        //getPanelBorder().getPanelCardSide().cardLayoutSide.show(PanelBorder.getPanelCardSide(), "ready");
        //JFenetre.instance.setVisible(true);
    }

    public void refreshReadyPlayers(HashMap<String, Player> playerHashMap) throws IOException {
        PanelBorder.getPanelCardSide().getPanelSideReady().refreshPlayers(playerHashMap);
    }

    public void refreshScore() throws IOException {
        PanelBorder.getPanelCardSide().getPanelSideScore().refreshScore();
    }

//    public void newRoundSong() throws IOException {
//        getPanelBorder().getPanelCardMain().getPanelMainPlay().refreshPanelMainPlay();
//    }

    public void newInfoSong() throws IOException {
        getPanelBorder().getPanelCardMain().getPanelMainInfoTrack().newInfoTrack();
    }

    public void switchtoGame(Track currentTrack) throws IOException {
        getPanelBorder().getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "gameMain");
        getPanelBorder().getPanelCardSide().cardLayoutSide.show(PanelBorder.getPanelCardSide(), "score");
        getPanelBorder().getPanelCardMain().getPanelMainPlay().initPanelMainPlay(currentTrack);
        getPanelBorder().getPanelCardSide().getPanelSideScore().initPanelSideScore();
        getPanelBorder().getPanelCardMain().getPanelMainPlay().startThread();
    }

    public void trackFinish(){
        getPanelBorder().getPanelCardMain().getPanelMainPlay().stopThread();
        getPanelBorder().getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "infosTrack");
    }

    public void gameFinish() throws IOException {
        panelFinal = new PanelFinal();
        jFenetre.setContentPane(panelFinal);
        jFenetre.setVisible(true);
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
