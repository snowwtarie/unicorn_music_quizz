package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Created by Stibo on 30/11/2016.
 */
public class PanelMainPlay extends JPanel implements ActionListener, KeyListener {
    private final JProgressBar bar;
    private JTextField jProposition = new JTextField();
    private JButton jButton = new JButton("Confirmer");
    private JPanel progress = new JPanel();
    private JPanel propositionPan = new JPanel();
    private JLabel label = new JLabel("Chanson : ");

    public PanelMainPlay(){
        bar = new JProgressBar();
        bar.setMaximum(500);
        bar.setMinimum(0);
        bar. setStringPainted (true);
        bar.setPreferredSize(new Dimension(100,50));

        progress.setLayout(new BorderLayout());
        progress.setPreferredSize(new DimensionUIResource(500, 500));
        progress.setBackground(Color.BLUE);
        progress.add(bar, BorderLayout.SOUTH);

        label.setPreferredSize(new DimensionUIResource(300, 100));

        this.setLayout(new BorderLayout());

        this.add(label, BorderLayout.NORTH);
        this.add(progress, BorderLayout.CENTER);

        propositionPan.setLayout(new BorderLayout());
        propositionPan.setPreferredSize(new DimensionUIResource(200, 50));
        propositionPan.add(jProposition, BorderLayout.CENTER);
        propositionPan.add(jButton, BorderLayout.EAST);
        this.add(propositionPan, BorderLayout.SOUTH);

        jButton. addActionListener( this );

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText("<html><body>Vous avez saisi: " + jProposition.getText() +"</body></html>");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
