package cs3500.music.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.ArrayList;

/**
 * Represents a piece of music by looking into each beat of a piece of music and what notes are
 * played in this particular beat.
 *
 * <p></p>A piece is a list of all the beats it contains and each beat is represented by a list
 * of notes in it.
 *
 * <p></p>There might be different structures for the music piece that the tests uses.
 */
public class Piece implements IMusicEditorModel {
  protected List<List<Note>> piece;

  /**
   * The constructor that takes in an existing piece of music. The input can be an empty piece.
   * However, if it's not empty, it must contain some notes. It also throws exception when
   * there's duplicate note at the same beat.
   *
   * @param piece   the input piece that's being copied
   */
  public Piece(List<List<Note>> piece) {
    Objects.requireNonNull(piece);
    if (!piece.isEmpty()) {
      boolean temp = true;
      for (List<Note> beat: piece) {
        temp = temp && beat.isEmpty();
      }
      if (temp) {
        throw new IllegalArgumentException("The piece can't be all empty beats!");
      }

      boolean temp2 = true;
      for (List<Note> beat: piece) {
        Set<Note> set = new HashSet<Note>(beat);
        temp2 = temp2 && (set.size() != beat.size());
      }
      if (temp2) {
        throw new IllegalArgumentException("There's same notes at the same beat!");
      }
    }
    this.piece = piece;
  }

  /**
   * Public default constructor that starts an empty piece of music.
   */
  public Piece() {
    this.piece = new ArrayList<List<Note>>();
  }

  /**
   * Public method to create one note at a time. It throws exception when the note already exists
   * in the piece or the duration or starting beat is invalid.
   *
   * @param note          the note being added
   */
  // change: only takes in one argument
  @Override
  public void add(Note note) {
    // check errors
    if (note.duration < 1) {
      throw new IllegalArgumentException("The note has to at least last for one beat!");
    }
    if (note.hit < 0) {
      throw new IllegalArgumentException("The starting beat cannot be negative!");
    }
    if (this.ifAddingExistingNote(note)) {
      throw new IllegalArgumentException("Invalid addition, the note already exists!");
    }

    // the index of the last beat
    int endBeat = note.hit + note.duration - 1;

    while (endBeat > this.piece.size() - 1) {
      this.piece.add(new ArrayList<Note>());
    }

    this.piece.get(note.hit).add(new NoteHead(note));

    for (int i = note.hit + 1; i <= endBeat; i ++) {
      this.piece.get(i).add(new Note(note));
    }
  }

  /**
   * Helper method to test if the note being added already exists in the beats.
   *
   * @param note      the note being added
   * @return          true if the note already exists in the beats
   */
  // change: only takes in one argument
  private boolean ifAddingExistingNote(Note note) {
    boolean result = false;
    int endBeat = note.hit + note.duration - 1;
    if (this.piece.isEmpty()) {
      return false;
    }
    if (endBeat > this.piece.size() - 1) {
      for (int i = note.hit; i < this.piece.size(); i ++) {
        result = result || this.piece.get(i).contains(note);
      }
      return result;
    }
    for (int i = note.hit; i <= endBeat; i ++) {
      result = result || this.piece.get(i).contains(note);
    }
    return result;
  }

  /**
   * Public method to remove a note and its note-sustains only if a note-head can be correctly
   * identified in the given starting beat. Also removes any empty last beat.
   *
   * @param note          the note being removed
   */
  // change: only takes in one argument now as Note class now has duration and hit as their
  // properties
  @Override
  public void remove(Note note) {
    if (note.hit < 0) {
      throw new IllegalArgumentException("starting beat can't be negative!");
    }
    if (note.hit > this.piece.size() - 1) {
      throw new IllegalArgumentException("The composition is not even that long!");
    }
    if (this.piece.isEmpty()) {
      throw new IllegalArgumentException("The composition has nothing to be removed!");
    }
    if (this.piece.get(note.hit).contains(note)) {
      if (this.piece.get(note.hit).get(this.piece.get(note.hit).indexOf(note)).hit == note.hit) {
        this.piece.get(note.hit).remove(note);
        for (int i = note.hit + 1;
             i < this.piece.size() && this.piece.get(i).contains(note) &&
                     this.piece.get(i).get(this.piece.get(i).indexOf(note)).hit != i; i ++) {
          this.piece.get(i).remove(note);
          while ((!this.piece.isEmpty()) && this.piece.get(this.piece.size() - 1).isEmpty()) {
            this.piece.remove(this.piece.size() - 1);
          }
        }
      }
      else {
        throw new IllegalArgumentException("That's not a note-head!");
      }
    }
    else {
      throw new IllegalArgumentException("The beat doesn't have the note!");
    }
  }

  /**
   * To edit an existing note. It throws exception when you can't identify the note in the piece
   * correctly, or when it's an invalid replacement.
   *
   * @param exist         the note being edited
   * @param replace       the replacement note
   */
  // change: only takes in two arguments now as starting beat and duration are fields of
  // Note class.
  @Override
  public void edit(Note exist, Note replace) {
    this.remove(exist);
    this.add(replace);
  }

  /**
   * Combine that piece with this piece in a simultaneous fashion.
   *
   * @param that    the piece being combined
   */
  @Override
  public void combineSim(Piece that) {
    Objects.requireNonNull(that);

    while (this.piece.size() < that.piece.size()) {
      this.piece.add(new ArrayList<Note>());
    }
    while (that.piece.size() < this.piece.size()) {
      that.piece.add(new ArrayList<Note>());
    }

    for (int i = 0; i < that.piece.size(); i ++) {
      for (Note note: that.piece.get(i)) {
        if (this.piece.get(i).contains(note)) {
          throw new IllegalArgumentException("Invalid combining, " +
                  "the piece contains same code at the same beat!");
        }
        this.piece.get(i).add(note);
      }
    }
  }

  /**
   * Adding that piece to the end of this piece.
   *
   * @param that  the piece of music being combined to this piece
   */
  @Override
  public void combineCon(Piece that) {
    Objects.requireNonNull(that);

    if (that.piece.isEmpty()) {
      return;
    }
    for (List<Note> beat: that.piece) {
      this.piece.add(new ArrayList<Note>());
      this.piece.get(this.piece.size() - 1).addAll(beat);
    }
  }

  /**
   * visualize a piece of music by creating a String visualization of the notes.
   *
   * @return      a string
   */
  @Override
  public String visualize() {
    // check error
    if (this.piece.isEmpty()) {
      throw new IllegalStateException("Nothing to be shown at the moment!");
    }
    int howManyNotes = this.getHighestNoteNumValue() - this.getLowestNoteNumValue() + 1;
    StringBuilder temp = new StringBuilder();
    temp = temp.append(this.getFirstLine(this.getLowestNoteNumValue()
            , this.getHighestNoteNumValue(), this.firstColumnLength()));
    for (int i = 0; i < this.piece.size(); i = i + 1) {
      StringBuilder line = new StringBuilder();
      line = line.append(String.format("%1$" +
              this.firstColumnLength() + "s", Integer.toString(i)));
      line = line.append(String.format("%1$" + howManyNotes * 5 + "s", " "));
      line = line.append("\n");
      for (Note note: this.piece.get(i)) {
        int noteIndexPosition = this.firstColumnLength() +
                ((note.representByNumber() - this.getLowestNoteNumValue()) * 5) + 3 - 1;
        line = line.replace(noteIndexPosition, noteIndexPosition + 1, note.print(i));
      }
      temp = temp.append(line);
    }
    return temp.toString();
  }


  /**
   * print the proper 5-space title according to the index of the note.
   *
   * @param index     index of the note
   * @return          string of the title of that note
   */
  @Override
  public String printIndexToTitle(int index) {
    switch (index) {
      case 1: return "  C1 ";
      case 2: return " C#1 ";
      case 3: return "  D1 ";
      case 4: return " D#1 ";
      case 5: return "  E1 ";
      case 6: return "  F1 ";
      case 7: return " F#1 ";
      case 8: return "  G1 ";
      case 9: return " G#1 ";
      case 10: return "  A1 ";
      case 11: return " A#1 ";
      case 12: return "  B1 ";

      case 13: return "  C2 ";
      case 14: return " C#2 ";
      case 15: return "  D2 ";
      case 16: return " D#2 ";
      case 17: return "  E2 ";
      case 18: return "  F2 ";
      case 19: return " F#2 ";
      case 20: return "  G2 ";
      case 21: return " G#2 ";
      case 22: return "  A2 ";
      case 23: return " A#2 ";
      case 24: return "  B2 ";

      case 25: return "  C3 ";
      case 26: return " C#3 ";
      case 27: return "  D3 ";
      case 28: return " D#3 ";
      case 29: return "  E3 ";
      case 30: return "  F3 ";
      case 31: return " F#3 ";
      case 32: return "  G3 ";
      case 33: return " G#3 ";
      case 34: return "  A3 ";
      case 35: return " A#3 ";
      case 36: return "  B3 ";

      case 37: return "  C4 ";
      case 38: return " C#4 ";
      case 39: return "  D4 ";
      case 40: return " D#4 ";
      case 41: return "  E4 ";
      case 42: return "  F4 ";
      case 43: return " F#4 ";
      case 44: return "  G4 ";
      case 45: return " G#4 ";
      case 46: return "  A4 ";
      case 47: return " A#4 ";
      case 48: return "  B4 ";

      case 49: return "  C5 ";
      case 50: return " C#5 ";
      case 51: return "  D5 ";
      case 52: return " D#5 ";
      case 53: return "  E5 ";
      case 54: return "  F5 ";
      case 55: return " F#5 ";
      case 56: return "  G5 ";
      case 57: return " G#5 ";
      case 58: return "  A5 ";
      case 59: return " A#5 ";
      case 60: return "  B5 ";

      case 61: return "  C6 ";
      case 62: return " C#6 ";
      case 63: return "  D6 ";
      case 64: return " D#6 ";
      case 65: return "  E6 ";
      case 66: return "  F6 ";
      case 67: return " F#6 ";
      case 68: return "  G6 ";
      case 69: return " G#6 ";
      case 70: return "  A6 ";
      case 71: return " A#6 ";
      case 72: return "  B6 ";

      case 73: return "  C7 ";
      case 74: return " C#7 ";
      case 75: return "  D7 ";
      case 76: return " D#7 ";
      case 77: return "  E7 ";
      case 78: return "  F7 ";
      case 79: return " F#7 ";
      case 80: return "  G7 ";
      case 81: return " G#7 ";
      case 82: return "  A7 ";
      case 83: return " A#7 ";
      case 84: return "  B7 ";

      case 85: return "  C8 ";
      case 86: return " C#8 ";
      case 87: return "  D8 ";
      case 88: return " D#8 ";
      case 89: return "  E8 ";
      case 90: return "  F8 ";
      case 91: return " F#8 ";
      case 92: return "  G8 ";
      case 93: return " G#8 ";
      case 94: return "  A8 ";
      case 95: return " A#8 ";
      case 96: return "  B8 ";

      case 97: return "  C9 ";
      case 98: return " C#9 ";
      case 99: return "  D9 ";
      case 100: return " D#9 ";
      case 101: return "  E9 ";
      case 102: return "  F9 ";
      case 103: return " F#9 ";
      case 104: return "  G9 ";
      case 105: return " G#9 ";
      case 106: return "  A9 ";
      case 107: return " A#9 ";
      case 108: return "  B9 ";

      case 109: return " C10 ";
      case 110: return " C#10";
      case 111: return " D10 ";
      case 112: return " D#10";
      case 113: return " E10 ";
      case 114: return " F10 ";
      case 115: return " F#10";
      case 116: return " G10 ";
      case 117: return " G#10";
      case 118: return " A10 ";
      case 119: return " A#10";
      case 120: return " B10 ";

      default: throw new IllegalArgumentException("invalid index");
    }
  }

  /**
   * get the length of the first column.
   *
   * @return   the length of the first column
   */
  private int firstColumnLength() {
    int temp = this.piece.size() - 1;
    return String.valueOf(temp).length();
  }

  /**
   * get the first title line of the visualization.
   *
   * @param lowest                  index of the lowest note
   * @param highest                 index of the highest note
   * @param firstColumnLength    the length of the first column
   * @return                     the string of the first line
   */
  private String getFirstLine(int lowest, int highest, int firstColumnLength) {
    StringBuilder temp = new StringBuilder();
    for (int i = 1; i <= firstColumnLength; i = i + 1) {
      temp = temp.append(" ");
    }
    for (int j = lowest; j <= highest; j = j + 1) {
      temp = temp.append(this.printIndexToTitle(j));
    }
    temp = temp.append("\n");
    return temp.toString();
  }

  /**
   * gets the lowest note's number value from the piece.
   *
   * @return   the index of the note
   */
  protected int getLowestNoteNumValue() {
    if (this.piece.isEmpty()) {
      throw new IllegalArgumentException("there is no note!");
    }
    int temp = 121;
    for (List<Note> beat: this.piece) {
      for (Note note: beat) {
        if (note.representByNumber() < temp) {
          temp = note.representByNumber();
        }
      }
    }
    return temp;
  }

  /**
   * gets the highest note's number value from the piece.
   *
   * @return    the index of the note
   */
  protected int getHighestNoteNumValue() {
    int temp = -1;
    for (List<Note> list: piece) {
      for (Note note: list) {
        if (note.representByNumber() > temp) {
          temp = note.representByNumber();
        }
      }
    }
    return temp;
  }

  /**
   * Give a copy of the field piece.
   *
   * @return    a copy of of the piece
   */
  @Override
  public List<List<Note>> getPiece() {
    List<List<Note>> copy = new ArrayList<List<Note>>();

    for (List<Note> beat: this.piece) {
      copy.add(new ArrayList<Note>());
      for (Note note: beat) {
        copy.get(copy.size() - 1).add(new Note(note));
      }
    }
    return copy;
  }
}
