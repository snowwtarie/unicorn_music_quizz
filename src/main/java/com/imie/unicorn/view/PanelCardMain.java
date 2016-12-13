package com.imie.unicorn.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Stibo on 30/11/2016.
 * Layout Secondaire / WEST SIDE du Layout Principal
 */
public class PanelCardMain extends JPanel{
    public final CardLayout cardLayout = new CardLayout();
    private static PanelMainPlay panelMainPlay;
    private static PanelMainInfoTrack panelMainInfoTrack;

    public static PanelMainPlay getPanelMainPlay() {
        return panelMainPlay;
    }
    public static PanelMainInfoTrack getPanelMainInfoTrack() {
        return panelMainInfoTrack;
    }

    public PanelCardMain(){

        panelMainPlay = new PanelMainPlay();
        panelMainInfoTrack = new PanelMainInfoTrack();

        this.setLayout(cardLayout);
        this.add("wait", new PanelMainWait());
        this.add("gameMain", panelMainPlay);
        this.add("infosTrack", panelMainInfoTrack);
        this.cardLayout.show(this, "wait");
    }

}
