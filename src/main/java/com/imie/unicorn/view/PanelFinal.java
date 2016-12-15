package com.imie.unicorn.view;

import com.imie.unicorn.controller.Player;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Stibo on 12/12/2016.
 */
public class PanelFinal extends JPanel implements ActionListener {
    private JLabel message = new JLabel("", SwingConstants.CENTER);
    private JButton reload = new JButton("Relancer");


    public PanelFinal() throws IOException {
        //initPanelFinal();
    }

    private void initPanelFinal() throws IOException {
        JFenetre.getInstance().getClient().sendMessage(new Message("GameEnd", null));
        //message.setText(gameWinner.getPseudo()+" est le vainqueur, Felicitations !");

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new DimensionUIResource(800, 800));
        message.setPreferredSize(new DimensionUIResource(800, 150));
        message.setFont(JFenetre.unicornFont);
        reload.addActionListener(this);
        this.add(message, BorderLayout.CENTER);
        this.add(reload, BorderLayout.SOUTH);
    }

    public void refreshPanelFinal() throws IOException {
        this.removeAll();
        initPanelFinal();
        this.repaint();
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == reload) {
            try {
                JFenetre.getInstance().launchUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
