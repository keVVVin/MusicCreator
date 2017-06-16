package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.Note;
import cs3500.music.model.NoteHead;
import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for Note class.
 */
public class NoteTest {
  // check equality
  @Test
  public void testEqual() {
    Note a = new Note(Pitch.A, 1);
    Note b = new Note(Pitch.A, 1);
    assertEquals(a, b);
  }

  @Test
  public void testEqual1() {
    Note a = new Note(Pitch.A, 1);
    NoteHead b = new NoteHead(a);
    assertEquals(a.equals(b), b.equals(a));
  }

  @Test
  public void testPrinting() {
    Note a = new Note(Pitch.CH, 10);
    assertEquals("C#10", a.toString());
  }
}
