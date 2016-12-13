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
    public final CardLayout cardLayoutSide = new CardLayout();
    private static PanelSideReady panelSideReady;
    private static PanelSideScore panelSideScore;

    public static PanelSideReady getPanelSideReady() {
        return panelSideReady;
    }
    public static PanelSideScore getPanelSideScore() {
        return panelSideScore;
    }

    public PanelCardSide(){
        panelSideReady = new PanelSideReady();
        panelSideScore = new PanelSideScore();

        this.setLayout(cardLayoutSide);
        this.add("ready", panelSideReady);
        this.add("score", panelSideScore);
        this.cardLayoutSide.show(this, "ready");
    }
}
