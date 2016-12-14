package com.imie.unicorn.view;


import com.imie.unicorn.controller.Client;
import com.imie.unicorn.controller.Track;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Stibo on 30/11/2016.
 */
public class PanelMainPlay extends JPanel implements ActionListener, KeyListener, FocusListener {
    private JProgressBar bar;
    private static JTextField jProposition = new JTextField();
    private JButton jButton = new JButton("Confirmer");
    private JPanel progress = new JPanel();
    private JPanel propositionPan = new JPanel();
    private JLabel label = new JLabel("", SwingConstants.CENTER);
    private JLabel wrongProp = new JLabel("", SwingConstants.CENTER);
    private Thread progressThread = new Thread(new Traitement());

    public static JTextField getProposition() {
        return jProposition;
    }

    public PanelMainPlay(){
        initPanelMainPlay();
    }

    private void initPanelMainPlay(){
        Track currentTrack = JFenetre.getInstance().getClient().getCurrentTrack();
        int numberSong = currentTrack.getId();
        label.setText("Chanson "+numberSong+"/25");
        label.setFont(JFenetre.robotoFont.deriveFont(25f));

        UIManager.put("ProgressBar.background", Color.GRAY );
        UIManager.put("ProgressBar.foreground", Color.RED);
        UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
        UIManager.put("ProgressBar.selectionForeground", Color.WHITE);


        bar = new JProgressBar();
        bar.setMaximum(3000);
        bar.setMinimum(0);
        bar. setStringPainted (true);
        bar.setPreferredSize(new Dimension(100,50));
        bar.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        bar.setBackground(Color.WHITE);


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

    public void refreshPanelMainPlay(){
        this.removeAll();
        initPanelMainPlay();
        this.repaint();
        this.revalidate();
    }

    public void startThread(){
        progressThread.start();
    }

    public void stopThread(){
        progressThread.interrupt();
    }

    class Traitement implements Runnable{
        public void run(){
            for ( int val = 0; val <= 3000; val++){
                bar.setValue( val ) ;
                try {Thread.sleep(10);} catch ( InterruptedException e) {}
            }
            JFenetre.getInstance().trackFinish();
        }
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
        Boolean trackFound = JFenetre.getInstance().getClient().checkProposition(jProposition.getText());
        if(!trackFound){
            jProposition.setText("");
            wrongProp.setText("Faux, Essaie Encore !");
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
            Boolean trackFound = JFenetre.getInstance().getClient().checkProposition(jProposition.getText());
            if(!trackFound){
                jProposition.setText("");
                wrongProp.setText("Faux, Essaie Encore !");
            }
        } else {
            wrongProp.setText("");
        }
    }
}
