package ui;

import model.Connect4Game;
import persistence.JsonReader;

import java.awt.event.ActionEvent;


import static ui.Connect4Frame.JSON_PATH;

/*
 * Handles Actions for the buttons in BoardPanel
 */
public class MenuEventHandler extends EventHandler {
    private JsonReader jsonReader;
    private Connect4Game game;
    private Connect4Frame frame;

    // EFFECTS: creates new MouseEventHandler with
    public MenuEventHandler(Connect4Game game, Connect4Frame frame) {
        this.game = game;
        this.frame = frame;
        this.jsonReader = new JsonReader(JSON_PATH);
    }

    // MODIFIES: game, frame
    // EFFECTS: updates frame and alters game
    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = e.getActionCommand();

        if (userInput.equals("Load Game")) {
            loadGame();
        } else if (userInput.equals("Play Again")) {
            newGame();
        }
        frame.update();

    }

    // MODIFIES: game
    // EFFECTS: creates a new game
    public void newGame() {
        Connect4Game newGame = new Connect4Game();
        game.setGameState(newGame.getGameState());
        game.setBoard(newGame.getBoard());
        game.setRecentlyPlaced(newGame.getRecentlyPlaced());
        game.setTurn(newGame.getTurn());
    }


    // CITATION: code taken from WorkRoomApp class from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: game
    // EFFECTS: loads workroom from file
    private void loadGame() {
        try {
            Connect4Game previousGame = jsonReader.read();
            game.setGameState(previousGame.getGameState());
            game.setBoard(previousGame.getBoard());
            game.setRecentlyPlaced(previousGame.getRecentlyPlaced());
            game.setTurn(previousGame.getTurn());

            //System.out.println("Loaded connect 4 game from " + JSON_PATH);
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JSON_PATH);
        }
    }
}