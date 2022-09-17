package ui;

import model.Connect4Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static model.Connect4Game.COLUMNS;
import static model.Connect4Game.ROWS;
import static ui.Connect4Frame.*;

/*
 * Panel containing Board
 */
public class BoardPanel extends JPanel {
    public static final int PIECE_DIAMETER = WIDTH / 7;
    private Connect4Game game;
    private List<JButton> buttons;
    private PiecePanelUI[][] boardUI;
    private GridBagConstraints gbc;
    private BoardEventHandler eventHandler;

    // EFFECTS: creates board UI
    public BoardPanel(Connect4Game game, Connect4Frame frame) {
        this.game = game;
        this.eventHandler = new BoardEventHandler(game, frame);
        this.buttons = new ArrayList<>();

        setBackground(BOARD_COLOUR);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        createButtons();
        createBoardUI();
    }

    // MODIFIES: this
    // EFFECTS: creates buttons so user can place pieces
    public void createButtons() {
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        for (int i = 0; i < COLUMNS; i++) {
            JButton button = new JButton();

            button.setContentAreaFilled(false);
            button.setOpaque(false);
            button.setBorderPainted(false);
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(eventHandler);
            button.addMouseListener(eventHandler);
            button.addKeyListener(eventHandler);
            gbc.gridx = i;
            gbc.gridheight = ROWS;
            button.setPreferredSize(new Dimension(PIECE_DIAMETER, PIECE_DIAMETER));
            buttons.add(button);
            add(button, gbc);
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates board grid with JPanels that represent pieces
    public void createBoardUI() {
        boardUI = new PiecePanelUI[ROWS][COLUMNS];
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        // Has to be set to 1 for the panels to be colourd properly
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(PIECE_INSETS, PIECE_INSETS, PIECE_INSETS, PIECE_INSETS);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                gbc.gridx = x;
                gbc.gridy = y;
                boardUI[y][x] = new PiecePanelUI(null);
                boardUI[y][x].setPreferredSize(new Dimension(PIECE_DIAMETER, PIECE_DIAMETER));
                add(boardUI[y][x], gbc);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: renders all the pieces onto the screen
    public void renderPieces() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                renderPiece(x, y);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: renders a single connect 4 piece onto the screen
    public void renderPiece(int x, int y) {
        if (game.getBoard()[y][x] == null) {
            boardUI[y][x].setPlayer(null);
        } else {
            boardUI[y][x].setPlayer(game.getBoard()[y][x].getPlayer());
        }

    }

    // MODIFIES: this
    // EFFECTS: prevents place piece buttons form being pressed after column is filled or game is over
    public void enableOrDisableButtons() {
        if (game.getGameState() != 0) {
            for (int i = 0; i < COLUMNS; i++) {
                buttons.get(i).setEnabled(false);
            }
        } else {
            for (int i = 0; i < COLUMNS; i++) {
                buttons.get(i).setEnabled(game.findEmptyRow(i) != -1);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: calls renderPieces() and enableOrDisableButtons();
    public void update() {
        renderPieces();
        enableOrDisableButtons();
    }
}
