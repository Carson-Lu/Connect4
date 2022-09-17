package ui;

import model.Connect4Game;
import model.Piece;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static model.Connect4Game.COLUMNS;
import static model.Connect4Game.ROWS;

// Connect 4 Console application
public class Connect4Console {
    public static final String JSON_PATH = "./data/connect4game.json";
    public static final String P1_PIECE = "O";
    public static final String P2_PIECE = "X";

    private Connect4Game game;
    private Scanner userInput;
    private String inputString;
    private int inputX;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: creates a new Connect4Console
    public Connect4Console() {
        jsonWriter = new JsonWriter(JSON_PATH);
        jsonReader = new JsonReader(JSON_PATH);
        game = new Connect4Game();
        userInput = new Scanner(System.in);
        playGame();
    }

    // MODIFIES: this
    // EFFECTS: calls runGame() and allows user to play again if desired
    public void playGame() {
        System.out.println("Welcome to my Connect 4 game! Please select one of the options:");
        printGameMenuOptions(game);
        while (true) {
            Scanner userPlayAgain = new Scanner(System.in);
            String playAgain = userPlayAgain.nextLine();

            if (playAgain.equals("p")) {
                game = new Connect4Game();
                runGame();
            } else if (playAgain.equals("l")) {
                loadGame();
                runGame();
            } else if (playAgain.equals("q")) {
                break;
            }
            System.out.println("Would you like to play again?");
            printGameMenuOptions(game);
            wipeSave();
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the game by printing board and using user input
    public void runGame() {
        while (true) {
            printBoard(game.getBoard());
            if (game.getGameState() != 0) {
                printGameOver();
                break;
            }
            printTurn();
            while (true) {
                getNewUserInput();
                try {
                    convertUserInputToInteger();
                    if (game.placePiece(inputX)) {
                        game.update();
                        break;
                    }
                } catch (Exception e) {
                    printBoard(game.getBoard());
                    System.out.println("Please place piece in valid position");
                    printTurn();
                }
            }
            saveGame();
        }
    }

    // EFFECTS: prints out
    // MODIFIES: this
    // EFFECTS: the game over line based on who won or if there was a draw.
    public void printGameOver() {
        switch (game.getGameState()) {
            case 1:
                System.out.println(P1_PIECE + " wins!");
                break;
            case 2:
                System.out.println(P2_PIECE + " wins!");
                break;
            case 3:
                System.out.println("Draw!");
                break;
        }
    }

    // EFFECTS: saves inputString field to user inputted string
    public void getNewUserInput() {
        this.inputString = userInput.nextLine();
    }

    // EFFECTS: converts user input to integer and saves it to field, throws NumberFormatExcepton
    //          if unable to parse user nput to an int
    public void convertUserInputToInteger() throws NumberFormatException {
        this.inputX = Integer.parseInt(inputString);
    }

    // MODIFIES: this
    // EFFECTS: prints out the game board for user to see
    public void printBoard(Piece[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == null) {
                    if (j == COLUMNS - 1) {
                        System.out.print("  ");
                    } else {
                        System.out.print("   |");
                    }
                } else {
                    if (j == COLUMNS - 1) {
                        System.out.print(" " + getPieceUI(board[i][j].getPlayer()) + " ");
                    } else {
                        System.out.print(" " + getPieceUI(board[i][j].getPlayer()) + " |");
                    }
                }
            }
            System.out.println();
            System.out.println("===========================");
        }
        System.out.println("[0] [1] [2] [3] [4] [5] [6]");
    }

    // EFFECTS: returns the piece ("O" or "X") based off of player's turn
    public String getPieceUI(Boolean player) {
        if (player) {
            return P2_PIECE;
        }
        return P1_PIECE;
    }

    // MODIFIES: this
    // EFFECTS: prints out game menu options
    public void printGameMenuOptions(Connect4Game game) {
        System.out.println("\t p: Play a new Connect 4 game");
        if (game.getGameState() == 0) {
            System.out.println("\t l: Load previous game");
        }
        System.out.println("\t q: Quit");
    }

    // MODIFIES: this
    // EFFECTS: prints out turn statement
    public void printTurn() {
        System.out.println(getPieceUI(game.getTurn()) + "'s turn.");
        System.out.println("Place a piece at a given location [0-6]:");
    }

    // REQUIRES: should only be called AFTER runGame() completes
    // EFFECTS: creates a new Connect4Game and saves it to this
    public void wipeSave() {
        game = new Connect4Game(); // Prevents constantly loading same completed game
        saveGame();
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

    // CITATION: code taken from WorkRoomApp class from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded connect 4 game from " + JSON_PATH);
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JSON_PATH);
        }
    }

}
