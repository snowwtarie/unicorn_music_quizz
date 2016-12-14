package com.imie.unicorn.view;

import com.imie.unicorn.model.Client;

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
    private static PanelBorder panelBorder;
    private static PanelFinal panelFinal;
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }



    private JFenetre(){
        this.setTitle ("Unicorn Music Quizzz");
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setSize(new DimensionUIResource(1210, 800));
        this.setBackground(Color.RED);
        this.setVisible(true);
        this.setLocationRelativeTo ( null ) ;
        this.setResizable(false);


    }

    public void init() throws IOException {

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
        boolean connexion = (Boolean) client.getConnection(pseudo);
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
        getPanelBorder().getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "wait");
        getPanelBorder().getPanelCardSide().cardLayoutSide.show(PanelBorder.getPanelCardSide(), "ready");
        getPanelBorder().getPanelCardSide().getPanelSideReady().initPanelSideReady();
    }

    public void refreshReadyPlayers(){
        PanelBorder.getPanelCardSide().getPanelSideReady().refreshPlayers();
    }

    public void refreshScore(){
        PanelBorder.getPanelCardSide().getPanelSideScore().refreshScore();
    }

    public void newRoundSong(){
        getPanelBorder().getPanelCardMain().getPanelMainPlay().refreshPanelMainPlay();
    }

    public void newInfoSong(){
        getPanelBorder().getPanelCardMain().getPanelMainInfoTrack().newInfoTrack();
    }

    public void switchtoGame(){
        getPanelBorder().getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "gameMain");
        getPanelBorder().getPanelCardMain().getPanelMainPlay().startThread();
        getPanelBorder().getPanelCardSide().cardLayoutSide.show(PanelBorder.getPanelCardSide(), "score");
    }

    public void trackFinish(){
        getPanelBorder().getPanelCardMain().getPanelMainPlay().stopThread();
        getPanelBorder().getPanelCardMain().cardLayout.show(PanelBorder.getPanelCardMain(), "infosTrack");
    }

    public void gameFinish(){
        panelFinal = new PanelFinal();
        JFenetre.instance.setContentPane(panelFinal);
        JFenetre.instance.setVisible(true);
    }
}
