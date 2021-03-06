package com.imie.unicorn.view;

import com.imie.unicorn.controller.Player;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    public PanelSideReady() throws IOException {
        initPanelSideReady(new HashMap<String, Player>());
    }
    public void initPanelSideReady(HashMap<String, Player> playerHashMap) throws IOException {

        try {
            ipLocale = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ipLocale);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.setLayout(new GridLayout(0,2));
        this.setBackground(Color.WHITE);

        JLabel pseudo = new JLabel("Pseudo");
        pseudo.setFont(JFenetre.robotoFont.deriveFont(20f));
        pseudo.setForeground(Color.RED);

        JLabel ready = new JLabel("Pret ?", SwingConstants.CENTER);
        ready.setFont(JFenetre.robotoFont.deriveFont(20f));
        ready.setForeground(Color.RED);

        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.add(pseudo);
        this.add(ready);
        for(Map.Entry<String, Player> p : playerHashMap.entrySet()) {
            if(p.getValue().getIp().equals(ipLocale)){
                JLabel myself = new JLabel("You");
                myself.setFont(JFenetre.robotoFont.deriveFont(20f));
                this.add(myself);
                readyBox.setBackground(Color.WHITE);
                readyBox.setFont(JFenetre.robotoFont.deriveFont(20f));
                this.add(readyBox);
                readyBox.addActionListener(this);
            } else {
                this.add(createJoueurLabel(p.getValue().getPseudo()));
                if(p.getValue().getIsReady()){
                    Icon waitRain = new ImageIcon(getClass().getClassLoader().getResource("ready.png"));
                    JLabel iconReady = new JLabel(waitRain);
                    this.add(iconReady);
                } else {
                    Icon notReady = new ImageIcon(getClass().getClassLoader().getResource("notready.png"));
                    JLabel iconNotReady = new JLabel(notReady);
                    this.add(iconNotReady);
                }
            }
        }
        this.setPreferredSize(new DimensionUIResource(300, 800));
        this.setVisible(true);
    }
    private JLabel createJoueurLabel(String pseudo){
        JLabel joueur = new JLabel(pseudo);
        joueur.setFont(JFenetre.robotoFont.deriveFont(20f));
        return joueur;
    }

    public void refreshPlayers(HashMap<String, Player> playerHashMap) throws IOException {
        this.removeAll();
        initPanelSideReady(playerHashMap);
        this.repaint();
        this.revalidate();

        System.out.println("Interface >>> REFRESH PLAYERS");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (readyBox.isSelected()){
            try {
                JFenetre.getInstance().getClient().sendMessage(new Message("PlayerReady", JFenetre.getInstance().getClient().getPlayer()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
