package cs3500.music;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Pitch;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.TextualView;
import cs3500.music.util.CompositionBuilderImpl;
import cs3500.music.view.VisualView;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import java.io.BufferedReader;
import java.io.InputStreamReader;



public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IView console = new TextualView();
    IView view = new VisualView();
    //MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...

    System.out.println("specify which file/song you would like to play: ");

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String song = input.readLine();

    IMusicEditorModel piece = MusicReader.parseFile(new FileReader(song), new CompositionBuilderImpl());
    System.out.println("specify which view you would like to use " +
            "(choose from \"console\", \"visual\" or \"midi\": ");
    BufferedReader input2 = new BufferedReader(new InputStreamReader(System.in));
    String viewType = input2.readLine();
    switch (viewType) {
      case "console":
        console.setPiece(piece);
        console.display();
        break;
      case "visual":
        view.setPiece(piece);
        view.display();
        break;
      case "midi":
        //midiView.setPiece(piece);
        //midiView.display();
        break;
      default:
        throw new IllegalArgumentException("invalid view type!");
    }
  }
}
