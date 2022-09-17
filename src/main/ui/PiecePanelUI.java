package ui;

import model.Piece;

import javax.swing.*;
import java.awt.*;

import static ui.BoardPanel.PIECE_DIAMETER;
import static ui.Connect4Frame.*;

/*
 * Class for making the pieces for the connect 4 board
 */
public class PiecePanelUI extends JPanel {
    private Boolean player;

    // EFFECTS: creates new PiecePaneUI
    public PiecePanelUI(Boolean player) {
        this.player = player;
    }

    public void setPlayer(Boolean player) {
        this.player = player;
    }

    // MODIFIES: this
    // EFFECTS: draws a circle that is the size of the panel
    @Override
    protected void paintComponent(Graphics g) {
        Color colour;
        if (player == null) {
            colour = NO_PIECE_COLOUR;
        } else {
            colour = getPieceColor(player);
        }
        g.setColor(colour);
        g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
    }
}
