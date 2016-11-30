package com.imie.unicorn.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stibo on 30/11/2016.
 * Layout Secondaire / WEST SIDE du Layout Principal
 */
public class PanelCardMain extends JPanel {
    private final CardLayout cardLayout;

    public PanelCardMain(){
        cardLayout = new CardLayout();
        this.add(new PanelMainWait());

    }
}
