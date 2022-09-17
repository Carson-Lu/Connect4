package ui;

import model.Connect4Game;
import ui.sound.PieceSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import static java.awt.event.KeyEvent.getKeyText;

/*
 * Event Handler for the BoardPanel class
 */
public class BoardEventHandler extends EventHandler implements KeyListener {
    public static final String SOUND_PATH = "data/placeConnect4Piece.wav";
    private Connect4Game game;
    private Connect4Frame frame;
    private PieceSound sound;

    // EFFECTS: has fields Connect4Game and Connect4Frame to work with
    public BoardEventHandler(Connect4Game game, Connect4Frame frame) {
        this.game = game;
        this.frame = frame;

        try {
            sound = new PieceSound(SOUND_PATH);
        } catch (Exception e) {
            System.err.println("Sound error");
        }
    }

    // MODIFIES: frame
    // EFFECTS: adds hover on button hover
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();

        // Makes mouse look like its clickable
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        button.setBorderPainted(true);
    }

    // MODIFIES: frame
    // EFFECTS: removes border from button after unhovered
    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setBorderPainted(false);
    }

    // MODIFIES: game, frame
    // EFFECTS: updates game and frame
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int x = Integer.parseInt(e.getActionCommand());
            userPerformedAction(x);
        } catch (Exception error) {
            // Do nothing
        }
    }

    // MODIFIES: game, frame
    // EFFECTS: updates game and frame
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int x = Integer.parseInt(getKeyText(e.getKeyCode())) - 1;
            userPerformedAction(x);
        } catch (Exception error) {
            // Do nothing
        }
    }

    // MODIFIES: game, frame
    // EFFECTS: updates game and frame
    public void userPerformedAction(int x) {
        if (game.placePiece(x)) {
            game.update();
            sound.play();
        }
        frame.update();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing
    }
}
