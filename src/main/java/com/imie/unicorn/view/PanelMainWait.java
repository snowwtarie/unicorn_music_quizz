package com.imie.unicorn.view;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;


/**
 * Created by Stibo on 30/11/2016.
 */
public class PanelMainWait extends JPanel {
    public PanelMainWait(){
        JLabel label = new JLabel("En Attente des Joueurs <3");
        this.add(label);
        this.setPreferredSize(new DimensionUIResource(200, 200));
        label.setAlignmentX(0.5f);
        label.setAlignmentY(0.5f);
        this.setVisible(true);
    }
}
