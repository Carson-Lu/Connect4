package ui.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/*
 * Plays sound for placing a piece
 */
public class PieceSound {
    private Clip clip;

    // EFFECTS: creates a clip and plays
    public PieceSound(String soundPath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    // MODIFIES: this
    // EFFECTS: plays the clip from the beginning
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }
}
