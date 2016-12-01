package com.imie.unicorn.view;

import com.imie.unicorn.controller.Client;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Stibo on 30/11/2016.
 * Layout Secondaire / EAST SIDE du Layout Principal
 */
public class PanelCardSide extends JPanel {
    private final CardLayout cardLayout;

    public PanelCardSide(){

        cardLayout = new CardLayout();
        this.add(new PanelSideReady());
        this.add(new PanelSideScore());
        this.setLayout(cardLayout);
        this.setVisible(true);

    }
}
