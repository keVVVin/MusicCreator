package cs3500.music.model;

import java.util.Objects;

/**
 * Represent a note in the Western system of music. In this implementation, a note
 * has a pitch and octave. A note here refers to one beat of the note in the Piece of music. The
 * actual duration of the note would be reflected as a property of the Piece of music instead of
 * being a property in the Note class.
 */
public class Note {
  protected final Pitch pitch;
  protected final int octave;
  // change: replaces Notehead in role.
  protected final int hit;
  // added new field: how many beats it sustains.
  protected final int duration;

  /**
   * The basic constructor of a note with the limitation that octave is non-negative and within
   * human hearing.
   *
   * @param pitch         the pitch of the note
   * @param octave        the octave of the note
   */
  public Note(Pitch pitch, int octave, int hit, int duration) {
    if (octave < 0) {
      throw new IllegalArgumentException("There is no negative octave");
    }
    if (octave > 10 || octave < 1) {
      throw new IllegalArgumentException("We only create note within human hearing in this" +
              "implementation!");
    }
    if (hit < 0) {
      throw new IllegalArgumentException("A note cannot start at a negative beat!");
    }
    if (duration < 0) {
      throw new IllegalArgumentException("The note should at least last for one beat!");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.hit = hit;
    this.duration = duration;
  }


  /**
   * Constructor that only
   * @param pitch
   * @param octave
   */
  public Note(Pitch pitch, int octave) {
    if (octave < 0) {
      throw new IllegalArgumentException("There is no negative octave");
    }
    if (octave > 10 || octave < 1) {
      throw new IllegalArgumentException("We only create note within human hearing in this" +
              "implementation!");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.hit = -1;
    this.duration = -1;
  }

  // might need to go
  /**
   * The copy constructor.
   * @param note    the note that's being copied
   */
  public Note(Note note) {
    Objects.requireNonNull(note);
    this.pitch = note.pitch;
    this.octave = note.octave;
    this.duration = note.duration;
    this.hit = note.hit;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Note) {
      return this.pitch == ((Note) o).pitch && this.octave == ((Note) o).octave;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.octave);
  }

  // mark for deletion
  /**
   * Helper method to check if a note is the note-head.
   *
   * @return true if the note is the note-head
   */
  protected boolean isNoteHead(int currentBeat) {
    if (this.hit == currentBeat) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return this.pitch.toString() + Integer.toString(this.octave);
  }

  /**
   * Helper method to represent a note by a number value, which represents its pitch and octave.
   *
   * @return    an integer representation of this note's pitch and octave
   */
  public int representByNumber() {
    return this.pitch.representByNumber() + (this.octave - 1) * 12;
  }

  // mark for deletion
  /**
   * Read a number value and convert it into a Note.
   *
   * @param num  number value of a note
   * @return    a Note

  public static Note readNumberValue(int num) {
    if (num < 1 || num > 120) {
      throw new IllegalArgumentException("Invalid number value of a note!");
    }
    int octave1 = ((num - 1) / 12) + 1;
    int remainder = num % 12;
    Pitch pitch1;
    switch (remainder) {
      case 1: pitch1 = Pitch.C;
              break;
      case 2: pitch1 = Pitch.CH;
              break;
      case 3: pitch1 = Pitch.D;
              break;
      case 4: pitch1 = Pitch.DH;
              break;
      case 5: pitch1 = Pitch.E;
              break;
      case 6: pitch1 = Pitch.F;
              break;
      case 7: pitch1 = Pitch.FH;
              break;
      case 8: pitch1 = Pitch.G;
              break;
      case 9: pitch1 = Pitch.GH;
              break;
      case 10: pitch1 = Pitch.A;
               break;
      case 11: pitch1 = Pitch.AH;
               break;
      case 12: pitch1 = Pitch.B;
               break;
      default: throw new IllegalArgumentException("invalid remainder!");
    }
    return new Note(pitch1, octave1);
  }
  */

  // mark for revision
  /**
   * Helper method to print note-sustain mark in a console.
   *
   * @return  a string
   */
  // change:
  protected String print(int currentBeat) {
    if (this.hit == currentBeat) {
      return "X";
    }
    return "|";
  }

  public int getHit() {
    return this.hit;
  }
}
