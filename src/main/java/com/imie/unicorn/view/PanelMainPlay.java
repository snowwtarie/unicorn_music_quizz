package com.imie.unicorn.view;


import com.imie.unicorn.controller.PlayerMp3;
import com.imie.unicorn.controller.Track;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
* Created by Stibo on 30/11/2016.
*/

public class PanelMainPlay extends JPanel implements ActionListener, KeyListener, FocusListener {
    private JProgressBar bar;
    private static JTextField jProposition;
    private JButton jButton;
    private JPanel progress;
    private JPanel propositionPan;
    private JLabel label;
    private JLabel wrongProp;
    private Thread progressThread;
    private Track currentTrack;

    public PlayerMp3 getPlayerMp3() {
        return playerMp3;
    }

    public void setPlayerMp3(PlayerMp3 playerMp3) {
        this.playerMp3 = playerMp3;
    }

    private PlayerMp3 playerMp3;

    public static JTextField getProposition() {
        return jProposition;
    }

    public PanelMainPlay(){

        this.jProposition =  new JTextField();
        this.jButton  = new JButton("Confirmer");
        this.progress = new JPanel();
        this.propositionPan = new JPanel();
        this.label =  new JLabel("", SwingConstants.CENTER);
        this.wrongProp = new JLabel();
        this.bar = new JProgressBar();
        bar.setMaximum(30);

        //Track currentTrack = JFenetre.getInstance().getClient().getCurrentTrack();


        label.setFont(JFenetre.robotoFont.deriveFont(25f));

        wrongProp.setFont(JFenetre.unicornFont.deriveFont(25f));
        wrongProp.setForeground(Color.RED);
        wrongProp.setBackground(Color.WHITE);
        wrongProp.setPreferredSize(new DimensionUIResource(800, 100));

        Icon rockUnicorn = new ImageIcon(getClass().getClassLoader().getResource("unicorn.gif"));
        JLabel rockUni = new JLabel(rockUnicorn);

        progress.setLayout(new BorderLayout());
        progress.setPreferredSize(new DimensionUIResource(500, 500));

        progress.setBackground(Color.WHITE);
        progress.add(wrongProp, BorderLayout.NORTH);
        progress.add(rockUni, BorderLayout.CENTER);
        progress.add(bar, BorderLayout.SOUTH);

        label.setPreferredSize(new DimensionUIResource(800, 100));

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.add(label, BorderLayout.NORTH);
        this.add(progress, BorderLayout.CENTER);

        jProposition.addKeyListener(this);
        jProposition.addFocusListener(this);
        jProposition.setFont(JFenetre.robotoFont.deriveFont(20f));

        propositionPan.setLayout(new BorderLayout());
        propositionPan.setPreferredSize(new DimensionUIResource(200, 100));
        propositionPan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        propositionPan.setBackground(Color.WHITE);

        JLabel titleProp = new JLabel("Votre proposition : ");
        titleProp.setFont(JFenetre.robotoFont.deriveFont(20f));
        propositionPan.add(titleProp , BorderLayout.NORTH);
        propositionPan.add(jProposition, BorderLayout.CENTER);
        propositionPan.add(jButton, BorderLayout.EAST);
        this.add(propositionPan, BorderLayout.SOUTH);
        jButton. addActionListener(this);
        this.setVisible(true);

    }

    public void initPanelMainPlay(Track currentTrack) throws IOException {
        this.currentTrack = currentTrack;
        this.bar.setValue(0);
        this.progressThread = new Thread(new Traitement());
        playerMp3 = new PlayerMp3(currentTrack);
        playerMp3.start();
        int numberSong = currentTrack.getId();
        label.setText("Chanson "+numberSong+" / 25");
    }

    public void refreshPanelMainPlay(Track currentTrack) throws IOException {
        initPanelMainPlay(currentTrack);
        this.repaint();
        this.revalidate();
    }

    public void startThread(){
        progressThread.start();
    }

    public void stopThread(){
        progressThread.stop();
    }

    class Traitement implements Runnable{
        public void run(){
            for ( int val = 0; val <= 30; val++){
                bar.setValue( val ) ;
                try {Thread.sleep(1000);} catch ( InterruptedException e) {}
            }
            try {
                JFenetre.getInstance().getPanelBorder().getPanelCardMain().getPanelMainInfoTrack().getMessage().setText("Personne n'a gagne");
                JFenetre.getInstance().trackFinish(currentTrack);
                JFenetre.getInstance().getClient().sendMessage(new Message("noWinner", null));
                playerMp3.kill();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JLabel getWrongProp() {
        return wrongProp;
    }

    public void setWrongProp(JLabel wrongProp) {
        this.wrongProp = wrongProp;
    }
    @Override
    public void focusGained(FocusEvent e) {
        wrongProp.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            JFenetre.getInstance().getClient().sendMessage(new Message("Proposition", jProposition.getText()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                JFenetre.getInstance().getClient().sendMessage(new Message("Proposition", jProposition.getText()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            wrongProp.setText("");
        }
    }
}
