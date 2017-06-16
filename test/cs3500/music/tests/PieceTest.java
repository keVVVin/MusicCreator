package cs3500.music.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.music.model.Note;
import cs3500.music.model.Piece;
import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the Piece class.
 */
public class PieceTest {
  Piece empty;
  Note a1;
  Note a2;
  Note a3;
  Piece p1;
  Piece p2;
  Piece p3;

  /**
   * Initializes some test objects.
   */
  public void initialize() {
    empty = new Piece();
    a1 = new Note(Pitch.A, 1, 0, 2);
    a2 = new Note(Pitch.A, 1, 1, 2);
    a3 = new Note(Pitch.A, 2, 1, 2);

    p1 = new Piece();
    p1.add(a1);

    p2 = new Piece();
    p2.add(a2);

    p3 = new Piece();
    p3.add(a3);
  }

  /**
   * Tests for the add method.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddError() {
    Piece piece = new Piece();
    piece.add(new Note(Pitch.A, 1, 1, 0));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddError1() {
    Piece piece = new Piece();
    piece.add(new Note(Pitch.A, 1, -1, 1));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddError2() {
    Piece piece = new Piece(new ArrayList<List<Note>>(Arrays.asList(
            new ArrayList<Note>(Arrays.asList(new Note(Pitch.A, 1, 0, 3))),
            new ArrayList<Note>(Arrays.asList(new Note(Pitch.A, 1, 0, 3))),
            new ArrayList<Note>(Arrays.asList(new Note(Pitch.A, 1, 0, 3)))
    )));
    piece.add(new Note(Pitch.A, 1, 1, 1));
  }

  @Test
  public void testAdd() {
    Piece piece = new Piece();
    piece.add(new Note(Pitch.A, 1, 4, 1));
    assertEquals(true, (piece.getPiece().size() == 5) &&
            piece.getPiece().get(4).contains(new Note(Pitch.A, 1)));
  }

  /**
   * Tests for remove method.
   */
  /**
  @Test (expected = IllegalArgumentException.class)
  public void testNegativeBeat() {
    this.initialize();
    empty.remove(a1, -1);
  }
   */

  @Test (expected = IllegalArgumentException.class)
  public void testNotLongEnough() {
    this.initialize();
    Note note = new Note(Pitch.A, 1, 2, 1);
    empty.remove(note);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNotNoteHead() {
    this.initialize();
    Note add = new Note(Pitch.A, 1, 0, 2);
    Note remove = new Note(Pitch.A, 1, 1, 2);
    empty.add(add);
    empty.remove(remove);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNoSuchNote() {
    this.initialize();
    Note add = new Note(Pitch.A, 1, 0, 2);
    Note remove = new Note(Pitch.A, 2, 0, 2);
    empty.add(add);
    empty.remove(remove);
  }

  @Test
  public void testRemove() {
    this.initialize();
    Note add1 = new Note(Pitch.A, 1, 1, 2);
    Note add2 = new Note(Pitch.A, 1, 0, 1);
    Note remove = new Note(Pitch.A, 1, 1, 2);
    empty.add(add1);
    empty.add(add2);
    empty.remove(remove);
    assertEquals(1, empty.getPiece().size());
  }

  /**
   * Tests for edit method.
   */
  // test that when you try to prolong a note but it would overlap with the same existing note
  @Test (expected = IllegalArgumentException.class)
  public void testOverlapping() {
    this.initialize();
    Note add1 = new Note(Pitch.A, 1, 0, 2);
    Note add2 = new Note(Pitch.A, 1, 2, 2);
    Note replace = new Note(Pitch.A, 1, 0, 3);
    empty.add(add1);
    empty.add(add2);
    empty.edit(add1, replace);
  }

  @Test
  public void testEdit() {
    this.initialize();
    Note add1 = new Note(Pitch.A, 1, 0, 2);
    Note replace = new Note(Pitch.A, 2, 0, 3);
    empty.add(add1);
    empty.edit(add1, replace);
    assertEquals(true, empty.getPiece().size() == 3 && empty.getPiece().get(2).contains(replace));
  }

  /**
   * Tests for combineSim() method.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidAdd() {
    this.initialize();
    p1.combineSim(p2);
  }

  @Test
  public void testCombineSim() {
    this.initialize();
    p1.combineSim(p3);
    assertEquals(true, p1.getPiece().size() == 3 && p1.getPiece().get(1).get(1).getHit() == 1
            && p1.getPiece().get(2).get(0).getHit() != 2);
  }

  /**
   * Tests for the Piece constructor.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAllEmpty() {
    Piece a = new Piece(new ArrayList<List<Note>>(Arrays.asList(
            new ArrayList<Note>()
    )));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDuplicate() {
    Piece a = new Piece(new ArrayList<List<Note>>(Arrays.asList(
            new ArrayList<Note>(Arrays.asList(new Note(Pitch.A, 1, 0, 1),
                    new Note(Pitch.A, 1, 0, 1)))
    )));
  }

  /**
   * Tests for combineCon() method.
   */
  @Test
  public void testCombineCon() {
    this.initialize();
    p1.combineCon(p2);
    assertEquals(p1.getPiece().size(), 5);
  }

  /**
   * Tests for visualize() method.
   */
  @Test
  public void testVisualize() {
    this.initialize();
    Note g3 = new Note(Pitch.G, 3, 0, 2);
    Note e4 = new Note(Pitch.E, 4, 0, 2);
    empty.add(g3);
    empty.add(e4);
    String expected = "   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 " + "\n" +
            "0  X                                            X  " + "\n" +
            "1  |                                            |  " + "\n";
    assertEquals(expected, empty.visualize());
  }

  @Test
  public void testVisualize1() {
    this.initialize();
    Note g3 = new Note(Pitch.G, 3, 0, 7);
    Note e4 = new Note(Pitch.E, 4, 0, 2);
    Note c4 = new Note(Pitch.C, 4, 4, 2);
    Note d4 = new Note(Pitch.D, 4, 2, 2);
    Note d42 = new Note(Pitch.D, 4, 6, 2);
    empty.add(g3);
    empty.add(c4);
    empty.add(d4);
    empty.add(d42);
    empty.add(e4);
    String expected2 = "   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4 " + "\n" +
            "0  X                                            X  " + "\n" +
            "1  |                                            |  " + "\n" +
            "2  |                                  X            " + "\n" +
            "3  |                                  |            " + "\n" +
            "4  |                        X                      " + "\n" +
            "5  |                        |                      " + "\n" +
            "6  |                                  X            " + "\n" +
            "7                                     |            " + "\n";
    assertEquals(expected2, empty.visualize());
  }

  @Test (expected = IllegalStateException.class)
  public void testVisualizeException() {
    this.initialize();
    empty.visualize();
  }

  /**
   * Tests for getPiece() method.
   */
  @Test
  public void testCopy() {
    this.initialize();
    Note g3 = new Note(Pitch.G, 3, 0, 7);
    Note e4 = new Note(Pitch.E, 4, 0, 2);
    Note c4 = new Note(Pitch.C, 4, 4, 2);
    Note d4 = new Note(Pitch.D, 4, 2, 2);
    Note d42 = new Note(Pitch.D, 4, 6, 2);
    empty.add(g3);
    empty.add(c4);
    empty.add(d4);
    empty.add(d42);
    empty.add(e4);
    assertEquals(empty.visualize(), new Piece(empty.getPiece()).visualize());
  }
}
