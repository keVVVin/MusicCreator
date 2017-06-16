package cs3500.music.view;

import cs3500.music.model.IMusicEditorModel;

/**
 * The interface of all view classes. The commonalities are the methods to set a music piece for
 * the view and to display.
 */
public interface IView {
  /**
   * It sets a piece for the view to display.
   * @param piece a music piece
   */
  void setPiece(IMusicEditorModel piece);

  /**
   * Displays the view.
   */
  void display();
}