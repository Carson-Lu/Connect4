package persistence;


import model.Connect4Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static model.Connect4Game.ROWS;
import static org.junit.jupiter.api.Assertions.*;

// CITATION:
// This class code is modeled after JsonWriterTest class in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Connect4Game game = new Connect4Game();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyConnect4Game() {
        try {
            Connect4Game game = new Connect4Game();
            JsonWriter writer = new JsonWriter("./data/testEmptyConnect4Game.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyConnect4Game.json");
            game = reader.read();

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGPartiallyFilledConnect4Game() {
        try {
            Connect4Game game = new Connect4Game();
            game.placePiece(0);
            game.placePiece(2);
            game.placePiece(4);
            game.update();
            game.placePiece(1);
            game.placePiece(2);
            game.placePiece(3);
            game.update();
            game.placePiece(1);
            game.update();
            game.placePiece(3);
            game.update();
            game.placePiece(2);
            game.update();
            /* Board looks like
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   | O |   |   |   |
            ===========================
               | O | X | X |   |   |
            ===========================
             O | X | O | X | O |   |
            ===========================
            [0] [1] [2] [3] [4] [5] [6]
             */
            JsonWriter writer = new JsonWriter("./data/partiallyFilledConnect4Game.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/partiallyFilledConnect4Game.json");
            game = reader.read();
            assertFalse(game.getBoard()[ROWS - 1][0].getPlayer());
            assertTrue(game.getTurn());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}