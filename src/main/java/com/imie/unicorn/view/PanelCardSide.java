package com.imie.unicorn.view;

import com.imie.unicorn.controller.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

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

    public PanelCardSide() throws IOException {
        panelSideReady = new PanelSideReady();
        panelSideScore = new PanelSideScore();

        this.setLayout(cardLayoutSide);
        this.add("ready", panelSideReady);
        this.add("score", panelSideScore);
        this.cardLayoutSide.show(this, "ready");
    }
}
