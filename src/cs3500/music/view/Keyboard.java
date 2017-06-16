package cs3500.music.view;

import java.awt.*;

import javax.swing.*;


public class Keyboard extends JPanel {
  int whiteWidth = 15;
  int whiteHeight = 200;
  int blackWidth = 10;
  int blackHeight = 90;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    setBackground(Color.WHITE);

    // draw white keys
    g.setColor(Color.BLACK);
    for (int i = 1; i <= 70; i = i + 1) {
      g.drawRect(30 + (i - 1) * whiteWidth, 0, whiteWidth, whiteHeight);
    }

    // draw black keys
    g.setColor(Color.BLACK);

    for (int i = 0; i < 10; i = i + 1) {

      g.fillRect(40 + i * 105, 0, blackWidth, blackHeight);
      g.fillRect(55 + i * 105, 0, blackWidth, blackHeight);
      g.fillRect(85 + i * 105, 0, blackWidth, blackHeight);
      g.fillRect(100 + i * 105, 0, blackWidth, blackHeight);
      g.fillRect(115 + i * 105, 0, blackWidth, blackHeight);

    }

  }

}
