package cs3500.music.model;

import java.util.List;

/**
 * The music editor tests interface, which specifies all the general functionality a music editor
 * tests should have.
 */
public interface IMusicEditorModel {
  /**
   * Public method to create one note at a time. It throws exception when the note already exists
   * in the piece or the duration or starting beat is invalid.
   *
   * @param note          the note being added
   */
  // change: only takes in one argument now as Note class now has duration and hit as their
  // properties
  void add(Note note);

  /**
   * Public method to remove a note and its note-sustains only if a note-head can be correctly
   * identified in the given starting beat. Also removes any empty last beat.
   *
   * @param note          the note being removed
   */
  // change: only takes in one argument now as Note class now has duration and hit as their
  // properties
  void remove(Note note);

  /**
   * To edit an existing note. It throws exception when you can't identify the note in the piece
   * correctly, or when it's an invalid replacement.
   *
   * @param exist         the note being edited
   * @param replace       the replacement note
   */
  void edit(Note exist, Note replace);

  /**
   * Combine that piece with this piece in a simultaneous fashion.
   *
   * @param that    the piece being combined
   */
  void combineSim(Piece that);

  /**
   * Adding that piece to the end of this piece.
   *
   * @param that  the piece of music being combined to this piece
   */
  void combineCon(Piece that);

  /**
   * visualize a piece of music by creating a String visualization of the notes.
   *
   * @return      a string
   */
  String visualize();

  /**
   * Give a copy of the field piece.
   *
   * @return    a copy of of the piece
   */
  List<List<Note>> getPiece();

  String printIndexToTitle(int index);
}
