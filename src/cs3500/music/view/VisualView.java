package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Piece;


public class VisualView implements IView {
  public static IMusicEditorModel VISUALPIECE;

  public VisualView() {
    VISUALPIECE = new Piece();
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Visual View");
    frame.setSize(2000, 1000);
    frame.setLayout(new GridLayout(2, 1));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    frame.add(new Display(VISUALPIECE));
    frame.add(new Keyboard());
    frame.setVisible(true);
  }


  @Override
  public void setPiece(IMusicEditorModel piece) {
    this.VISUALPIECE = piece;
  }

  @Override
  public void display() {
    main(null);
  }
}
