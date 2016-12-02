package com.imie.unicorn.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stibo on 30/11/2016.
 * Layout Secondaire / WEST SIDE du Layout Principal
 */
public class PanelCardMain extends JPanel {
    public final CardLayout cardLayout = new CardLayout();;

    public PanelCardMain(){
        this.setLayout(cardLayout);
        this.add(new PanelMainWait());
        this.add(new PanelMainInfoTrack());
    }
}
