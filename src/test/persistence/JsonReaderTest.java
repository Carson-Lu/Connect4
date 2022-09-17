package persistence;

import model.Connect4Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
// CITATION:
// This class code is modeled after JsonReaderTest class in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonReaderTest  {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Connect4Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyConnect4Game() {
        JsonReader reader = new JsonReader("./data/testEmptyConnect4Game.json");
        try {
            Connect4Game game = reader.read();
            game.placePiece(0);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderPartiallyFilledConnect4Game() {
        JsonReader reader = new JsonReader("./data/partiallyFilledConnect4Game.json");
        try {
            Connect4Game game = reader.read();
            assertEquals(0, game.getGameState());
            assertTrue(game.getTurn());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}