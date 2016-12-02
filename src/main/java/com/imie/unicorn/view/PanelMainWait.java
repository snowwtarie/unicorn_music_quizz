package com.imie.unicorn.view;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Stibo on 30/11/2016.
 */
public class PanelMainWait extends JPanel {
    public PanelMainWait(){
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 100,0));
        Icon waitUnicorn = new ImageIcon(getClass().getClassLoader().getResource("Unicorn.jpg"));
        JLabel label = new JLabel(waitUnicorn);
        JLabel titre = new JLabel("<html><br><br>Unicorn Music Quizz<br><br></html>");
        titre.setFont(JFenetre.unicornFont);
        this.setPreferredSize(new DimensionUIResource(850,900));
        this.setBackground(Color.WHITE);
        this.add(titre);
        this.add(label);
        this.setVisible(true);
    }

}
