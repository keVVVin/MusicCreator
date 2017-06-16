package cs3500.music.model;

// the whole class is deleted now.
/**
 * A note-head represents the starting beat of a note in a piece of music.
 */
public class NoteHead extends Note {
  /**
   * The basic constructor of a note.
   *
   * @param pitch  the pitch of the note
   * @param octave the octave of the note
   */
  public NoteHead(Pitch pitch, int octave) {
    super(pitch, octave);
  }

  /**
   * The copy constructor.
   * @param note    the note that's being copied
   */
  public NoteHead(Note note) {
    super(note);
  }


  protected boolean isNoteHead() {
    return true;
  }


  protected String print() {
    return "X";
  }
}
