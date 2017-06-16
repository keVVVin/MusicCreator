package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import java.util.List;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;


public class Display extends JPanel {
  IMusicEditorModel piece;
  int lowestNote;
  int highestNote;
  int rows;
  int columns;
  int width = 100;
  int height = 15;
  int xStart = 50;
  int yStart = 20;
  int pitchXStart = 10;
  int pitchYStart = 30;
  int noteWidth = 25;
  int noteHeight = 15;


  public Display(IMusicEditorModel model) {
    this.piece = model;
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    List<List<Note>> piece = model.getPiece();
    highestNote = -1;
    for (List<Note> list: piece) {
      for (Note note: list) {
        if (note.representByNumber() > highestNote) {
          highestNote = note.representByNumber();
        }
      }
    }
    lowestNote = 121;
    for (List<Note> beat: piece) {
      for (Note note: beat) {
        if (note.representByNumber() < lowestNote) {
          lowestNote = note.representByNumber();
        }
      }
    }



    this.rows = highestNote - lowestNote + 1;
    this.columns = this.piece.getPiece().size() / 4 + 2;


  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // pitch titles
    for (int i = 1; i <= rows; i = i + 1) {
      g.drawString(this.piece.printIndexToTitle(lowestNote + i - 1),
              pitchXStart, pitchYStart + ((i - 1) * height));
    }

    // header
    for (int i = 1; i <= columns; i = i + 1) {
      g.drawString(Integer.toString((i - 1) * 4), xStart + (i - 1) * width, yStart);
    }

    // draw note heads
    g.setColor(Color.BLACK);
    for (int i = 0; i < this.piece.getPiece().size(); i = i + 1) {
      for (Note note: this.piece.getPiece().get(i)) {
        if (note.getHit() == i) {
          g.fillRect(xStart + i * noteWidth,
                  yStart + (note.representByNumber() - lowestNote) * noteHeight,
                  noteWidth, noteHeight);
        }
      }
    }

    // draw note sustains
    g.setColor(Color.GREEN);
    for (int i = 0; i < this.piece.getPiece().size(); i = i + 1) {
      for (Note note: this.piece.getPiece().get(i)) {
        if (note.getHit() != i) {
          g.fillRect(xStart + i * noteWidth,
                  yStart + (note.representByNumber() - lowestNote) * noteHeight,
                  noteWidth, noteHeight);
        }
      }
    }

    // the grid background
    g.setColor(Color.BLACK);
    for (int i = 1; i <= rows; i = i + 1) {
      for (int j = 1; j <= columns; j = j + 1) {
        g.drawRect(xStart + (j - 1) * width, yStart + (i - 1) * height, width, height);
      }
    }
    g.drawRect(xStart, yStart, width, height);
  }
}
