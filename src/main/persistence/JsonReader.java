package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Connect4Game;
import model.Piece;
import org.json.*;

import static model.Connect4Game.COLUMNS;
import static model.Connect4Game.ROWS;

// Represents a reader that reads connect4game from JSON data stored in file
// CITATION:
// This class code is modeled after JsonReader class in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads connect4game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Connect4Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseConnect4Game(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Converts given JSONObject to a Connect4Game
    private Connect4Game parseConnect4Game(JSONObject jsonObject) {
        Connect4Game savedGame = new Connect4Game();
        savedGame.setGameState(jsonObject.getInt("gameState"));

        try {
            jsonObject.getJSONObject("recentlyPlaced").get("posX");
        } catch (Exception e) {
            savedGame.setRecentlyPlaced(null);
        }

        addPiecesToSavedGame(savedGame, jsonObject);

        if (jsonObject.getBoolean("turn")) {
            savedGame.nextTurn();
        }

        return savedGame;
    }

    // MODIFIES: savedGame
    // EFFECTS: converts jsonObject into pieces and adds those pieces onto savedGame
    private void addPiecesToSavedGame(Connect4Game savedGame, JSONObject jsonObject) {
        JSONArray jsonBoard;
        jsonBoard = jsonObject.getJSONArray("board");
        for (int y = ROWS - 1; y >= 0; y--) {
            for (int x = 0; x < COLUMNS; x++) {
                try {
                    JSONObject jsonPiece = jsonBoard.getJSONArray(y).getJSONObject(x);
                    correctTurn(savedGame, jsonPiece, "player");
                    savedGame.placePiece(x);
                } catch (Exception e) {
                    // Do nothing
                }
            }
        }
    }

    // MODIFIES: savedGame
    // EFFECTS: sets the game to the correct turn
    private void correctTurn(Connect4Game savedGame, JSONObject jsonPiece, String key) throws Exception {
        if (savedGame.getTurn() != jsonPiece.getBoolean(key)) {
            savedGame.nextTurn();
        }
    }

}

