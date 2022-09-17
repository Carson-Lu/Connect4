package ui;

import model.Connect4Game;
import model.EventLog;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import static ui.EventLogPrinter.printLog;

/*
 * Window where the Connect 4 game will be played
 */
public class Connect4Frame extends JFrame {
    public static final String JSON_PATH = "./data/connect4game.json";
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    public static final Color PLAYER1_COLOUR = Color.RED;
    public static final Color PLAYER2_COLOUR = Color.YELLOW;
    public static final Color NO_PIECE_COLOUR = Color.LIGHT_GRAY;
    public static final Color BOARD_COLOUR = new Color(0, 114, 191);
    public static final String PLAYER1_COLOUR_STRING = "Red";
    public static final String PLAYER2_COLOUR_STRING = "Yellow";
    public static final int PIECE_INSETS = 15;

    private JsonWriter jsonWriter;
    private Connect4Game game;
    private MenuPanel mp;
    private BoardPanel bp;

    // Constructs main frame
    // EFFECTS: sets up window where Connect4Game can be played
    public Connect4Frame() {
        super("Connect 4");

        this.jsonWriter = new JsonWriter(JSON_PATH);

        game = new Connect4Game();
        mp = new MenuPanel(game, this);
        bp = new BoardPanel(game, this);
        add(mp, BorderLayout.NORTH);
        add(bp);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        pack();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                printLog(EventLog.getInstance());
            }
        });
    }

    public void update() {
        bp.update();
        mp.update();
        saveGame();
        repaint();
    }


    // EFFECTS: Returns colour based off of turn
    public static Color getPieceColor(Boolean player) {
        if (player) {
            return PLAYER2_COLOUR;
        }
        return PLAYER1_COLOUR;
    }

    // CITATION: code taken from WorkRoomApp class from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the workroom to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_PATH);
        }
    }


}
