package com.imie.unicorn.view;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Stibo on 30/11/2016.
 * Layout Principal de JFenetre
 */
public class PanelBorder extends JPanel {

    private static PanelCardMain panelCardMain;
    private static PanelCardSide panelCardSide;

    public static PanelCardMain getPanelCardMain() {
        return panelCardMain;
    }

    public static PanelCardSide getPanelCardSide() {
        return panelCardSide;
    }

    public PanelBorder() throws IOException {
        panelCardMain = new PanelCardMain();
        panelCardSide = new PanelCardSide();

        this.setLayout(new BorderLayout());
        this.setBackground(Color.RED);
        this.add(panelCardMain, BorderLayout.WEST);
        this.add(panelCardSide, BorderLayout.EAST);

        this.getComponent(0).setBackground(Color.WHITE);
        this.getComponent(0).setPreferredSize(new DimensionUIResource(850, 800));

        this.getComponent(1).setBackground(Color.BLUE);
        this.getComponent(1).setPreferredSize(new DimensionUIResource(350, 800));

        this.setVisible(true);
    }


}
