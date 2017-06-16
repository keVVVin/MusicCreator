package cs3500.music.view;

import cs3500.music.model.IMusicEditorModel;


public class TextualView implements IView {
  IMusicEditorModel model;

  @Override
  public void setPiece(IMusicEditorModel piece) {
    this.model = piece;

  }

  @Override
  public void display() {
    System.out.println(this.model.visualize());
  }
}
