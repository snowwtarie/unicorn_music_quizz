package com.imie.unicorn.view;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.Track;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Stibo on 1/12/2016.
 */
public class PanelMainInfoTrack extends JPanel {
    public JLabel getMessage() {
        return message;
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }

    private JLabel message;
    private JPanel trackInfos;
    private JLabel trackTitle;
    private JLabel infos;
    private ImageIcon visuel;
    private JLabel trackVisuel;

    public PanelMainInfoTrack(){
        this.message = new JLabel("Personne n'a trouver !", SwingConstants.CENTER);
        this.trackInfos = new JPanel();
        this.trackTitle  = new JLabel("DFGHJKL:LKJHGFDDFGHJKL:", SwingConstants.CENTER);
        this.infos  = new JLabel("Preparez vous, une nouvelle chanson va demarrer !", SwingConstants.CENTER);

        visuel = new ImageIcon();
        trackVisuel = new JLabel(visuel);

        trackTitle.setPreferredSize(new DimensionUIResource(500,100));
        trackTitle.setFont(JFenetre.robotoFont);
        trackTitle.setBackground(Color.BLUE);
        trackInfos.setLayout(new BorderLayout());
        trackInfos.add(trackTitle, BorderLayout.NORTH);
        trackInfos.add(trackVisuel, BorderLayout.CENTER);
        trackInfos.setBackground(Color.WHITE);
        trackInfos.setVisible(true);

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new DimensionUIResource(800, 800));
        message.setPreferredSize(new DimensionUIResource(800, 100));
        message.setFont(JFenetre.unicornFont);
        infos.setPreferredSize(new DimensionUIResource(800, 100));
        infos.setFont(JFenetre.unicornFont.deriveFont(20f));
        this.add(message, BorderLayout.NORTH);
        this.add(trackInfos, BorderLayout.CENTER);
        this.add(infos , BorderLayout.SOUTH);
    }


    public void initPanelMainInfoTrack(Track track) throws IOException {

        Track lastTrack = track;
        URL url = null;
        try {
            url = new URL(lastTrack.getAlbumPicture());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        visuel.setImage(img);
        trackTitle.setText("<html>La Reponse etait : "+lastTrack.getTitle()+" - "+lastTrack.getArtist()+"</html>");
    }

    public void newInfoTrack(Track currentTrack) throws IOException {
        initPanelMainInfoTrack(currentTrack);
        this.repaint();
        this.revalidate();
        this.getParent().repaint();
        this.getParent().revalidate();
    }
}
