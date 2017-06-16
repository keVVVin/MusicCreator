package cs3500.music.model;

/**
 * Represents twelve pitches in the Western system of music, which are
 * C C♯ D D♯ E F F♯ G G♯ A A♯ B. Here we represent 12 pitches by C CH D DH E F FH G GH A AH B
 * because of the limitation that # is an illegal character as enum keyword.
 */
public enum Pitch {
  C, CH, D, DH, E, F, FH, G, GH, A, AH, B;

  @Override
  public String toString() {
    switch (this) {
      case C: return "C";
      case CH: return "C#";
      case D: return "D";
      case DH: return "D#";
      case E: return "E";
      case F: return "F";
      case FH: return "F#";
      case G: return "G";
      case GH: return "G#";
      case A: return "A";
      case AH: return "A#";
      case B: return "B";
      default: throw new IllegalArgumentException("invalid pitch");
    }
  }

  /**
   * Helper method to represent a pitch by a number value.
   *
   * @return    an integer
   */
  protected int representByNumber() {
    switch (this) {
      case C: return 1;
      case CH: return 2;
      case D: return 3;
      case DH: return 4;
      case E: return 5;
      case F: return 6;
      case FH: return 7;
      case G: return 8;
      case GH: return 9;
      case A: return 10;
      case AH: return 11;
      case B: return 12;
      default: throw new IllegalArgumentException("invalid pitch");
    }
  }
}
