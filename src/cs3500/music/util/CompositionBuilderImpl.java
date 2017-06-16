package cs3500.music.util;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Piece;
import cs3500.music.model.Pitch;

public class CompositionBuilderImpl implements CompositionBuilder<IMusicEditorModel> {
  private IMusicEditorModel model;

  public CompositionBuilderImpl() {
    this.model = new Piece();
  }
  @Override
  public IMusicEditorModel build() {
    return this.model;
  }

  @Override
  public CompositionBuilder<IMusicEditorModel> setTempo(int tempo) {
    return null;
  }

  @Override
  public CompositionBuilder<IMusicEditorModel> addNote(int start, int end, int instrument, int pitch, int volume) {
    int hit = start;
    int duration = end - start;
    int octave = pitch / 12 - 1;
    int remainder = pitch % 12;
    Pitch notePitch;
    switch (remainder) {
      case 0: notePitch = Pitch.C;
        break;
      case 1: notePitch = Pitch.CH;
        break;
      case 2: notePitch = Pitch.D;
        break;
      case 3: notePitch = Pitch.DH;
        break;
      case 4: notePitch = Pitch.E;
        break;
      case 5: notePitch = Pitch.F;
        break;
      case 6: notePitch = Pitch.FH;
        break;
      case 7: notePitch = Pitch.G;
        break;
      case 8: notePitch = Pitch.GH;
        break;
      case 9: notePitch = Pitch.A;
        break;
      case 10: notePitch = Pitch.AH;
        break;
      case 11: notePitch = Pitch.B;
        break;
      default: throw new IllegalArgumentException("invalid remainder!");
    }
    this.model.add(new Note(notePitch, octave, hit, duration));
    return this;
  }
}
