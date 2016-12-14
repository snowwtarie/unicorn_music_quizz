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
    private JLabel message = new JLabel("", SwingConstants.CENTER);
    private JPanel trackInfos = new JPanel();
    private JLabel trackTitle = new JLabel("", SwingConstants.CENTER);
    private JLabel infos = new JLabel("Preparez vous, une nouvelle chanson va demarrer !", SwingConstants.CENTER);

    public PanelMainInfoTrack(){

    }

    public void initPanelMainInfoTrack() throws IOException {
        Track lastTrack = JFenetre.getInstance().getClient().getCurrentTrack();

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

        ImageIcon visuel = new ImageIcon(img);
        JLabel trackVisuel = new JLabel(visuel);
        trackTitle.setPreferredSize(new DimensionUIResource(500,100));
        trackTitle.setFont(JFenetre.robotoFont);
        trackTitle.setText("<html>La Reponse etait : "+lastTrack.getTitle()+" - "+lastTrack.getArtist()+"</html>");
        trackInfos.setLayout(new BorderLayout());
        trackInfos.add(trackTitle, BorderLayout.NORTH);
        trackInfos.add(trackVisuel, BorderLayout.CENTER);
        trackInfos.setPreferredSize(new DimensionUIResource(500,500));
        trackInfos.setBackground(Color.WHITE);
        trackInfos.setVisible(true);

        Player winner = JFenetre.getInstance().getClient().getRoundWinner();
        if(winner == null){
            message.setText("Personne n'a gagne !");
        } else {
            message.setText(winner.getPseudo()+" a trouver la bonne reponse !");
        }

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new DimensionUIResource(800, 800));
        message.setPreferredSize(new DimensionUIResource(800, 150));
        message.setFont(JFenetre.unicornFont);
        infos.setPreferredSize(new DimensionUIResource(800, 150));
        infos.setFont(JFenetre.unicornFont.deriveFont(20f));
        this.add(message, BorderLayout.NORTH);
        this.add(trackInfos, BorderLayout.CENTER);
        this.add(infos , BorderLayout.SOUTH);
    }

    public void newInfoTrack() throws IOException {
        this.removeAll();
        initPanelMainInfoTrack();
        this.repaint();
        this.revalidate();
    }
}
