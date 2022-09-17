package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
 *  Represents Connect 4 Game
 */
public class Connect4Game implements Writable {
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;

    private boolean turn; // False is Player 1's turn, True is player 2's turn
    private Piece[][] board;
    private int gameState; // 0: no Winner, 1: P1 wins, 2: P2 wins, 3: Draw
    private Piece recentlyPlaced;

    // Creates a Connect Four Game
    // EFFECTS: Creates Connect Four game with board of size COLUMNS x ROWS
    public Connect4Game() {
        this.turn = false;
        this.board = new Piece[ROWS][COLUMNS];
        this.gameState = 0;
        recentlyPlaced = null;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public Piece[][] getBoard() {
        return this.board;
    }

    public int getGameState() {
        return this.gameState;
    }

    public Piece getRecentlyPlaced() {
        return this.recentlyPlaced;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public void setRecentlyPlaced(Piece recentlyPlaced) {
        this.recentlyPlaced = recentlyPlaced;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    // REQUIRES: update() should only be called after and iff placePiece() is successful
    // MODIFIES: this
    // EFFECTS: updates the game, will see if there is a winner
    public void update() {
        determineWinner(recentlyPlaced);
        if (gameState == 0) {
            nextTurn();
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the turn (boolean)
    public void nextTurn() {
        turn = !turn;
    }

    // REQUIRES: column (x) location to be 0 < COLUMNS
    // MODIFIES: this
    // EFFECTS: places a Piece onto Board at given x location, if successful return true, otherwise false
    //          also changes recentlyPlaced to newly placed piece
    public boolean placePiece(int x) {
        int y = findEmptyRow(x);
        if (y != -1) {
            board[y][x] = new Piece(x, y, turn);
            recentlyPlaced = board[y][x];
            EventLog.getInstance().logEvent(new Event("Player " + getPlayerNumber(this.getTurn())
                    + " placed a piece at " + x));
            return true;
        }
        return false;
    }

    // EFFECTS: returns row (y value) closest to bottom of board if possible, if not returns -1
    public int findEmptyRow(int x) {
        for (int y = ROWS - 1; y >= 0; y--) {
            if (board[y][x] == null) {
                return y;
            }
        }
        return -1;
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // MODIFIES: this
    // EFFECTS: determines if there is a winner or not
    public void determineWinner(Piece recentlyPlaced) {
        boolean foundFourInARow = false;
        List<Integer> loi = new ArrayList<>();
        loi.add(countNumPiecesVertical(recentlyPlaced.getPosY()));
        loi.add(countNumPiecesHorizontal(recentlyPlaced.getPosX()));
        loi.add(countNumPiecesMainDiagonal(recentlyPlaced.getPosX(), recentlyPlaced.getPosY()));
        loi.add(countNumPiecesCounterDiagonal(recentlyPlaced.getPosX(), recentlyPlaced.getPosY()));

        for (Integer i : loi) {
            if (i >= 4) {
                foundFourInARow = true;
                break;
            }
        }

        if (foundFourInARow) {
            setPlayerWinner();
        } else if (checkDraw()) {
            this.gameState = 3;
        }
    }

    // REQUIRES: current player played the winning piece
    // MODIFIES: this
    // EFFECTS: if a player wins, sets the winner to them
    //          Player1 wins, gameState = 1
    //          Player2 wins, gameState = 2
    public void setPlayerWinner() {
        if (turn) {
            this.gameState = 2;
        } else {
            this.gameState = 1;
        }
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECTS: returns number of pieces (by the same player) placed in a vertical column, based on last piece placed
    public int countNumPiecesVertical(int y) {
        if (y >= ROWS) {
            return 0;
        } else if (this.turn == board[y][recentlyPlaced.getPosX()].getPlayer()) {
            return 1 + countNumPiecesVertical(y + 1);
        }
        return 0;
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECTS: returns number of pieces (by the same player) placed in a horizontal row, based on last piece placed
    public int countNumPiecesHorizontal(int x) {
        return 1 + countNumPiecesHorizontalLeft(x - 1) + countNumPiecesHorizontalRight(x + 1);
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECTS: returns number of pieces (by the same player) placed to the right of given piece
    public int countNumPiecesHorizontalRight(int x) {
        if ((x >= COLUMNS) || board[recentlyPlaced.getPosY()][x] == null) {
            return 0;
        } else if (this.turn == board[recentlyPlaced.getPosY()][x].getPlayer()) {
            return 1 + countNumPiecesHorizontalRight(x + 1);
        }
        return 0;
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECTS: returns number of peices (by the same player) placed to the left of given piece
    public int countNumPiecesHorizontalLeft(int x) {
        if ((x < 0) || board[recentlyPlaced.getPosY()][x] == null) {
            return 0;
        } else if (this.turn == board[recentlyPlaced.getPosY()][x].getPlayer()) {
            return 1 + countNumPiecesHorizontalLeft(x - 1);
        }
        return 0;
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECTS: returns number of pieces (by the same player) that are in a row diagonally (top left to bottom right)
    public int countNumPiecesMainDiagonal(int x, int y) {
        return 1 + countNumPiecesUpLeft(x - 1, y - 1) + countNumPiecesDownRight(x + 1, y + 1);
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECT: returns number of pieces (by the same player) that are in a row up and left of the given position
    public int countNumPiecesUpLeft(int x, int y) {
        if ((x < 0) || (y < 0) || board[y][x] == null) {
            return 0;
        } else if (this.turn == board[y][x].getPlayer()) {
            return 1 + countNumPiecesUpLeft(x - 1, y - 1);
        }
        return 0;
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECT: returns number of pieces (by the same player) that are in a row down and right of the given position
    public int countNumPiecesDownRight(int x, int y) {
        if ((x >= COLUMNS) || (y >= ROWS) || board[y][x] == null) {
            return 0;
        } else if (this.turn == board[y][x].getPlayer()) {
            return 1 + countNumPiecesDownRight(x + 1, y + 1);
        }
        return 0;
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECTS: returns number of pieces (by the same player) that are in a row diagonally (bottom left to top right)
    public int countNumPiecesCounterDiagonal(int x, int y) {
        return 1 + countNumPiecesDownLeft(x - 1, y + 1) + countNumPiecesUpRight(x + 1, y - 1);
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECT: returns number of pieces (by the same player) that are in a row down and left of the given position
    public int countNumPiecesDownLeft(int x, int y) {
        if ((x < 0) || (y >= ROWS) || board[y][x] == null) {
            return 0;
        } else if (this.turn == board[y][x].getPlayer()) {
            return 1 + countNumPiecesDownLeft(x - 1, y + 1);
        }
        return 0;
    }

    // REQUIRES: recentlyPlaced must be a Piece and not null
    // EFFECT: returns number of pieces (by the same player) that are in a row Up and to the right of given position
    public int countNumPiecesUpRight(int x, int y) {
        if ((x >= COLUMNS) || (y < 0) || board[y][x] == null) {
            return 0;
        } else if (this.turn == board[y][x].getPlayer()) {
            return 1 + countNumPiecesUpRight(x + 1, y - 1);
        }
        return 0;
    }

    // REQUIRES: check if there is a winner before calling this method
    // EFFECTS: check if there is a draw, returns true if there is
    //          will produce true even if there is a winner
    public boolean checkDraw() {
        for (int i = 0; i < COLUMNS; i++) {
            if (this.board[0][i] == null) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: converts fields to json
    // CITATION:
    // This class code is modeled after WorkRoom class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("turn", turn);
        json.put("gameState", gameState);
        try {
            json.put("recentlyPlaced", recentlyPlaced.toJson());
        } catch (Exception e) {
            json.put("recentlyPlaced", "null");
        }

        json.put("board", boardToJson());
        return json;
    }

    // EFFECTS: returns board as a 2D JSONArray
    // CITATION:
    // This class code is modeled after WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public JSONArray boardToJson() {
        JSONArray jsonArrayOfBoardRows = new JSONArray();
        for (int y = 0; y < ROWS; y++) {
            JSONArray jsonArray = new JSONArray();
            for (int x = 0; x < COLUMNS; x++) {
                if (board[y][x] == null) {
                    JSONObject emptyPiece = new JSONObject();
                    emptyPiece.put("posY", y);
                    emptyPiece.put("posX", x);
                    emptyPiece.put("player", "null");
                    jsonArray.put(emptyPiece);
                } else {
                    jsonArray.put(board[y][x].toJson());
                }
            }
            jsonArrayOfBoardRows.put(jsonArray);
        }
        return jsonArrayOfBoardRows;
    }

    // EFFECTS: converts player to an integer (1 or 2)
    // for the purposes of logging events
    public static int getPlayerNumber(Boolean player) {
        if (player) {
            return 2;
        }
        return 1;
    }


}
