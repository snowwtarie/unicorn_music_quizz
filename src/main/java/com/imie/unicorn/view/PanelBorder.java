package com.imie.unicorn.view;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;

/**
 * Created by Stibo on 30/11/2016.
 * Layout Principal de JFenetre
 */
public class PanelBorder extends JPanel {
    public PanelBorder(){
        this.setLayout(new BorderLayout());
        this.add(new PanelCardMain(), BorderLayout.WEST);
        this.add(new PanelCardSide(), BorderLayout.EAST);

        this.getComponent(0).setBackground(Color.WHITE);
        this.getComponent(0).setPreferredSize(new DimensionUIResource(850, 800));

        this.getComponent(1).setBackground(Color.BLUE);
        this.getComponent(1).setPreferredSize(new DimensionUIResource(350, 800));
    }
}
