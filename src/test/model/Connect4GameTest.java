package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Connect4Game.COLUMNS;
import static model.Connect4Game.ROWS;
import static org.junit.jupiter.api.Assertions.*;
import static ui.Connect4Console.P1_PIECE;
import static ui.Connect4Console.P2_PIECE;

class Connect4GameTest {
    Connect4Game emptyBoard, diagonalWinBoard, vertHorizWinBoard, almostDrawBoard;

    @BeforeEach
    public void setup() {
        emptyBoard = new Connect4Game();
        /* Board looks like:
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
            [0] [1] [2] [3] [4] [5] [6]
         */

        diagonalWinBoard = new Connect4Game();
        for (int y = ROWS - 1; y > ROWS - 4; y--) {
            for (int x = 0; x < COLUMNS; x++) {
                diagonalWinBoard.getBoard()[y][x] = new Piece(x, y, ((x + y) % 2 == 0));
            }
        }
        diagonalWinBoard.getBoard()[3][2] = null;
        diagonalWinBoard.getBoard()[3][3] = null;
        diagonalWinBoard.getBoard()[2][1] = new Piece(1, 2, false);
        diagonalWinBoard.getBoard()[2][4] = new Piece(4, 2, true);
        /* partlyFilledBoard looks like:
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               | O |   |   | X |   |
            ===========================
             O | X |   |   | O | X | O
            ===========================
             X | O | X | O | X | O | X
            ===========================
             O | X | O | X | O | X | O
            ===========================
            [0] [1] [2] [3] [4] [5] [6]
         */

        vertHorizWinBoard = new Connect4Game();
        for (int y = ROWS - 1; y > ROWS - 4; y--) {
            vertHorizWinBoard.getBoard()[y][COLUMNS - 2] = new Piece(COLUMNS - 2, y, true);
            vertHorizWinBoard.getBoard()[y][COLUMNS - 1] = new Piece(COLUMNS - 1, y, false);
        }

        /* verticalHorizontalWinBoard looks like:
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   |   |   |   |   |
            ===========================
               |   |   |   |   | X | O
            ===========================
               |   |   |   |   | X | O
            ===========================
               |   |   |   |   | X | O
            ===========================
            [0] [1] [2] [3] [4] [5] [6]
         */


        almostDrawBoard = new Connect4Game();
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                if ((y == 0) && (x == 0)) {
                    almostDrawBoard.getBoard()[y][x] = null;
                } else {
                    if (y % 3 == 0) {
                        almostDrawBoard.getBoard()[y][x] = new Piece(x, y, (x % 2 == 0));
                    } else {
                        almostDrawBoard.getBoard()[y][x] = new Piece(x, y, (x % 2 == 1));
                    }

                }
            }
        }
        almostDrawBoard.getBoard()[0][2] = new Piece(2, 0, false);
        /* almostDrawBoard looks like:
               | O | O | O | X | O | X
            ===========================
             O | X | O | X | O | X | O
            ===========================
             O | X | O | X | O | X | O
            ===========================
             X | O | X | O | X | O | X
            ===========================
             O | X | O | X | O | X | O
            ===========================
             O | X | O | X | O | X | O
            ===========================
            [0] [1] [2] [3] [4] [5] [6]
         */
    }

    @Test
    public void updateTest() {
        // Checking Vertical Win
        assertFalse(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 1));
        vertHorizWinBoard.update();
        assertEquals(1, vertHorizWinBoard.getGameState());

        vertHorizWinBoard.nextTurn(); // Forcing a next turn to be made
        assertTrue(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 2));
        vertHorizWinBoard.update();
        assertEquals(2, vertHorizWinBoard.getGameState());

        // Check nobody wins
        assertFalse(emptyBoard.getTurn());
        assertTrue(emptyBoard.placePiece(0));
        assertTrue(emptyBoard.placePiece(1));
        assertTrue(emptyBoard.placePiece(2));
        emptyBoard.update();
        assertEquals(0, emptyBoard.getGameState());
        assertTrue(emptyBoard.getTurn());

        // Checking Horizontal Win
        assertTrue(emptyBoard.getTurn());
        emptyBoard.nextTurn();// Forcing turn back to player 1 (O's)
        assertFalse(emptyBoard.getTurn());
        assertTrue(emptyBoard.placePiece(3));
        emptyBoard.update();
        assertEquals(1, emptyBoard.getGameState());
        assertFalse(emptyBoard.getTurn());


        // Checking Main Diagonal Win
        assertFalse(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(5));
        diagonalWinBoard.update();
        assertEquals(1, diagonalWinBoard.getGameState());

        // Checking Counter Diagonal Win
        diagonalWinBoard.nextTurn();// Forcing next turn even though game has "ended" (for testing purposes)
        assertTrue(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(0));
        diagonalWinBoard.update();
        assertEquals(2, diagonalWinBoard.getGameState());

        // Checking Draw
        assertEquals(0, almostDrawBoard.getGameState());
        almostDrawBoard.nextTurn();
        assertTrue(almostDrawBoard.getTurn());
        assertTrue(almostDrawBoard.placePiece(0));
        almostDrawBoard.update();
        assertEquals(3, almostDrawBoard.getGameState());
    }

    @Test
    public void testSetBoard() {
        diagonalWinBoard.setBoard(emptyBoard.getBoard());
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                assertNull(diagonalWinBoard.getBoard()[y][x]);
            }
        }
    }

    @Test
    public void testSetTurn() {
        assertFalse(emptyBoard.getTurn());
        emptyBoard.setTurn(true);
        assertTrue(emptyBoard.getTurn());
        emptyBoard.setTurn(false);
        assertFalse(emptyBoard.getTurn());
    }

    @Test
    public void nextTurnTest() {
        assertFalse(emptyBoard.getTurn());
        assertFalse(diagonalWinBoard.getTurn());
        assertFalse(vertHorizWinBoard.getTurn());
        assertFalse(almostDrawBoard.getTurn());
        emptyBoard.nextTurn();
        diagonalWinBoard.nextTurn();
        vertHorizWinBoard.nextTurn();
        almostDrawBoard.nextTurn();
        assertTrue(emptyBoard.getTurn());
        assertTrue(diagonalWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.getTurn());
        assertTrue(almostDrawBoard.getTurn());
    }


    @Test
    public void placePieceTest() {
        // Fills the empty board
        for (int j = ROWS - 1; j >= 0; j--) {
            for (int i = 0; i < COLUMNS; i++) {
                assertTrue(emptyBoard.placePiece(i));
            }
        }

        // Tries to piece at every location after emptyBoard is filled
        for (int i = 0; i < COLUMNS; i++) {
            assertFalse(emptyBoard.placePiece(i));
        }

    }

    @Test
    public void findEmptyRowTest() {
        for (int i = 0; i < COLUMNS; i++) {
            assertEquals(ROWS - 1, emptyBoard.findEmptyRow(i));
        }

        assertEquals(2, diagonalWinBoard.findEmptyRow(0));
        assertEquals(1, diagonalWinBoard.findEmptyRow(1));
        assertEquals(3, diagonalWinBoard.findEmptyRow(2));
        assertEquals(3, diagonalWinBoard.findEmptyRow(3));
        assertEquals(1, diagonalWinBoard.findEmptyRow(4));
        assertEquals(2, diagonalWinBoard.findEmptyRow(5));
        assertEquals(2, diagonalWinBoard.findEmptyRow(6));

    }

    @Test
    public void determineWinnerTest() {
        // NOTE: Technically there are "multiple winners" on this same board, however the method
        // only tests for a winner based off of last piece placed so this is checking that,
        // when a winning piece (or one that causes a draw) is placed, gameState will change

        // Checking Vertical Win
        assertFalse(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 1));
        assertEquals(vertHorizWinBoard.getBoard()[2][COLUMNS - 1], vertHorizWinBoard.getRecentlyPlaced());
        vertHorizWinBoard.determineWinner(vertHorizWinBoard.getRecentlyPlaced());
        assertEquals(1, vertHorizWinBoard.getGameState());

        vertHorizWinBoard.nextTurn();
        assertTrue(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 2));
        assertEquals(vertHorizWinBoard.getBoard()[2][COLUMNS - 2], vertHorizWinBoard.getRecentlyPlaced());
        vertHorizWinBoard.determineWinner(vertHorizWinBoard.getRecentlyPlaced());
        assertEquals(2, vertHorizWinBoard.getGameState());

        // Check nobody wins
        assertFalse(emptyBoard.getTurn());
        assertTrue(emptyBoard.placePiece(0));
        assertTrue(emptyBoard.placePiece(1));
        assertTrue(emptyBoard.placePiece(2));
        emptyBoard.determineWinner(emptyBoard.getRecentlyPlaced());
        assertEquals(0, emptyBoard.getGameState());

        // Checking Horizontal Win
        assertTrue(emptyBoard.placePiece(3));
        emptyBoard.determineWinner(emptyBoard.getRecentlyPlaced());
        assertEquals(1, emptyBoard.getGameState());

        // Checking Main Diagonal Win
        assertFalse(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(5));
        assertEquals(diagonalWinBoard.getBoard()[2][5], diagonalWinBoard.getRecentlyPlaced());
        diagonalWinBoard.determineWinner(diagonalWinBoard.getRecentlyPlaced());
        assertEquals(1, diagonalWinBoard.getGameState());

        // Checking Counter Diagonal Win
        assertFalse(diagonalWinBoard.getTurn());
        diagonalWinBoard.nextTurn();
        assertTrue(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(0));
        assertEquals(diagonalWinBoard.getBoard()[2][0], diagonalWinBoard.getRecentlyPlaced());
        diagonalWinBoard.determineWinner(diagonalWinBoard.getRecentlyPlaced());
        assertEquals(2, diagonalWinBoard.getGameState());

        // Checking Draw
        assertEquals(0, almostDrawBoard.getGameState());
        almostDrawBoard.nextTurn();
        assertTrue(almostDrawBoard.getTurn());
        assertTrue(almostDrawBoard.placePiece(0));
        almostDrawBoard.determineWinner(almostDrawBoard.getRecentlyPlaced());
        assertEquals(3, almostDrawBoard.getGameState());
    }

    @Test
    public void setPlayerWinner() {
        assertFalse(emptyBoard.getTurn());
        assertEquals(0, emptyBoard.getGameState());

        emptyBoard.setPlayerWinner();
        assertFalse(emptyBoard.getTurn());
        assertEquals(1, emptyBoard.getGameState());

        emptyBoard.nextTurn();
        assertTrue(emptyBoard.getTurn());
        emptyBoard.setPlayerWinner();
        assertEquals(2, emptyBoard.getGameState());
    }

    @Test
    public void countNumPiecesVerticalTest() {
        // Bottom left
        assertFalse(emptyBoard.getTurn());
        assertTrue(emptyBoard.placePiece(0));
        assertEquals(1, emptyBoard.countNumPiecesVertical(emptyBoard.getRecentlyPlaced().getPosY()));

        // Middle
        assertFalse(emptyBoard.getTurn());
        assertTrue(emptyBoard.placePiece(COLUMNS / 2));
        assertEquals(1, emptyBoard.countNumPiecesVertical(emptyBoard.getRecentlyPlaced().getPosY()));

        // Bottom Right
        assertTrue(emptyBoard.placePiece(COLUMNS - 1));
        assertEquals(1, emptyBoard.countNumPiecesVertical(emptyBoard.getRecentlyPlaced().getPosY()));
        assertFalse(emptyBoard.getTurn());

        // Placing piece onto prexisting piece of the same type
        assertFalse(emptyBoard.getTurn());
        assertTrue(emptyBoard.placePiece(0));
        assertEquals(2, emptyBoard.countNumPiecesVertical(emptyBoard.getRecentlyPlaced().getPosY()));

        // Place on top of stack of same player pieces, null on left, nothing (wall) on right, same piece below
        assertFalse(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 1));
        assertEquals(4, vertHorizWinBoard.countNumPiecesVertical(vertHorizWinBoard.getRecentlyPlaced().getPosY()));

        // Place onto same stack but different piece, null on left, nothing (wall) on right, different piece below
        vertHorizWinBoard.nextTurn();
        assertTrue(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 1));
        assertEquals(1, vertHorizWinBoard.countNumPiecesVertical(vertHorizWinBoard.getRecentlyPlaced().getPosY()));

        // Place onto stack beside, null on left, same piece on right, different piece below
        vertHorizWinBoard.nextTurn();
        assertFalse(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 1));
        assertEquals(1, vertHorizWinBoard.countNumPiecesVertical(vertHorizWinBoard.getRecentlyPlaced().getPosY()));
    }

    @Test
    public void countNumPiecesHorizontal() {
        // Bottom left
        assertTrue(emptyBoard.placePiece(0));
        assertEquals(1, emptyBoard.countNumPiecesHorizontal(emptyBoard.getRecentlyPlaced().getPosX()));
        // Middle
        assertTrue(emptyBoard.placePiece(COLUMNS / 2));
        assertEquals(1, emptyBoard.countNumPiecesHorizontal(emptyBoard.getRecentlyPlaced().getPosX()));

        // Bottom Right
        assertTrue(emptyBoard.placePiece(COLUMNS - 1));
        assertEquals(1, emptyBoard.countNumPiecesHorizontal(emptyBoard.getRecentlyPlaced().getPosX()));

        // Placing piece onto prexisting piece from same player
        assertTrue(emptyBoard.placePiece(0));
        assertEquals(1, emptyBoard.countNumPiecesHorizontal(emptyBoard.getRecentlyPlaced().getPosX()));

        // Placing to the right of preexisting piece from the same player
        assertTrue(emptyBoard.placePiece(1));
        assertEquals(2, emptyBoard.countNumPiecesHorizontal(emptyBoard.getRecentlyPlaced().getPosX()));

        // Placing to the left of a preexisting piece from the same player
        assertTrue(emptyBoard.placePiece(COLUMNS - 2));
        assertEquals(2, emptyBoard.countNumPiecesHorizontal(emptyBoard.getRecentlyPlaced().getPosX()));

        // Placing piece between two pieces from the same player
        assertTrue(vertHorizWinBoard.placePiece(0));
        assertTrue(vertHorizWinBoard.placePiece(2));
        assertTrue(vertHorizWinBoard.placePiece(1));
        assertEquals(3, vertHorizWinBoard.countNumPiecesHorizontal(vertHorizWinBoard.getRecentlyPlaced().getPosX()));
    }

    @Test
    public void countNumPiecesMainDiagonalTest() {
        // Bottom left
        assertTrue(emptyBoard.placePiece(0));
        assertEquals(1, emptyBoard.countNumPiecesMainDiagonal(emptyBoard.getRecentlyPlaced().getPosX(),
                emptyBoard.getRecentlyPlaced().getPosY()));

        // Right above bottom left
        assertTrue(emptyBoard.placePiece(0));
        assertEquals(1, emptyBoard.countNumPiecesMainDiagonal(emptyBoard.getRecentlyPlaced().getPosX(),
                emptyBoard.getRecentlyPlaced().getPosY()));

        // Middle
        assertTrue(emptyBoard.placePiece(COLUMNS / 2));
        assertEquals(1, emptyBoard.countNumPiecesMainDiagonal(emptyBoard.getRecentlyPlaced().getPosX(),
                emptyBoard.getRecentlyPlaced().getPosY()));

        // Bottom Right
        assertTrue(emptyBoard.placePiece(COLUMNS - 1));
        assertEquals(1, emptyBoard.countNumPiecesMainDiagonal(emptyBoard.getRecentlyPlaced().getPosX(),
                emptyBoard.getRecentlyPlaced().getPosY()));

        // Checks up left and down right
        assertFalse(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(2));
        assertEquals(4, diagonalWinBoard.countNumPiecesMainDiagonal(diagonalWinBoard.getRecentlyPlaced().getPosX()
                , diagonalWinBoard.getRecentlyPlaced().getPosY()));

        // Checks down and to the right, wall (nothing) at up left
        diagonalWinBoard.nextTurn();
        assertTrue(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(0));
        assertEquals(4, diagonalWinBoard.countNumPiecesMainDiagonal(diagonalWinBoard.getRecentlyPlaced().getPosX()
                , diagonalWinBoard.getRecentlyPlaced().getPosY()));

        assertTrue(diagonalWinBoard.placePiece(1));
        diagonalWinBoard.nextTurn();
        assertFalse(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(2));
        assertEquals(1, diagonalWinBoard.countNumPiecesMainDiagonal(diagonalWinBoard.getRecentlyPlaced().getPosX()
                , diagonalWinBoard.getRecentlyPlaced().getPosY()));

        // Surrounds left and right of newly tested pieces by the opposite player
        vertHorizWinBoard.nextTurn();
        assertTrue(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 4));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 4));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 4));
        vertHorizWinBoard.nextTurn();
        assertFalse(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 3));
        assertEquals(1, vertHorizWinBoard.countNumPiecesMainDiagonal(vertHorizWinBoard.getRecentlyPlaced().getPosX()
                , vertHorizWinBoard.getRecentlyPlaced().getPosY()));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 3));
        assertEquals(1, vertHorizWinBoard.countNumPiecesMainDiagonal(vertHorizWinBoard.getRecentlyPlaced().getPosX()
                , vertHorizWinBoard.getRecentlyPlaced().getPosY()));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 3));
        assertEquals(1, vertHorizWinBoard.countNumPiecesMainDiagonal(vertHorizWinBoard.getRecentlyPlaced().getPosX()
                , vertHorizWinBoard.getRecentlyPlaced().getPosY()));

        // Case for y < 0
        assertTrue(almostDrawBoard.placePiece(0));
        assertEquals(1, almostDrawBoard.countNumPiecesMainDiagonal(almostDrawBoard.getRecentlyPlaced().getPosX()
                , almostDrawBoard.getRecentlyPlaced().getPosY()));

        almostDrawBoard.getBoard()[0][1] = null;
        assertTrue(almostDrawBoard.placePiece(1));
        assertEquals(2, almostDrawBoard.countNumPiecesMainDiagonal(almostDrawBoard.getRecentlyPlaced().getPosX()
                , almostDrawBoard.getRecentlyPlaced().getPosY()));

    }

    @Test
    public void countNumPiecesCounterDiagonalTest() {
        // Bottom left, down left out of bounds, up right null
        assertTrue(emptyBoard.placePiece(0));
        assertEquals(1, emptyBoard.countNumPiecesCounterDiagonal(emptyBoard.getRecentlyPlaced().getPosX(),
                emptyBoard.getRecentlyPlaced().getPosY()));

        // Middle, down left out of bounds, up right null
        assertTrue(emptyBoard.placePiece(COLUMNS / 2));
        assertEquals(1, emptyBoard.countNumPiecesCounterDiagonal(emptyBoard.getRecentlyPlaced().getPosX(),
                emptyBoard.getRecentlyPlaced().getPosY()));

        // Bottom Right, down left and up right both out of bounds
        assertTrue(emptyBoard.placePiece(COLUMNS - 1));
        assertEquals(1, emptyBoard.countNumPiecesCounterDiagonal(emptyBoard.getRecentlyPlaced().getPosX(),
                emptyBoard.getRecentlyPlaced().getPosY()));

        // 2 down left and one up right
        diagonalWinBoard.nextTurn();
        assertTrue(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(3));
        assertEquals(4, diagonalWinBoard.countNumPiecesCounterDiagonal(diagonalWinBoard.getRecentlyPlaced().getPosX()
                , diagonalWinBoard.getRecentlyPlaced().getPosY()));

        // 3 down left, wall (nothing) up right
        assertTrue(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(COLUMNS - 1));
        assertEquals(4, diagonalWinBoard.countNumPiecesCounterDiagonal(diagonalWinBoard.getRecentlyPlaced().getPosX()
                , diagonalWinBoard.getRecentlyPlaced().getPosY()));

        // Up right and down left are both different pieces
        assertTrue(diagonalWinBoard.getTurn());
        assertTrue(diagonalWinBoard.placePiece(3));
        diagonalWinBoard.nextTurn();
        assertFalse(diagonalWinBoard.getTurn());
        assertEquals(1, diagonalWinBoard.countNumPiecesCounterDiagonal(diagonalWinBoard.getRecentlyPlaced().getPosX()
                , diagonalWinBoard.getRecentlyPlaced().getPosY()));

        // Surrounds left and right of newly tested pieces by the opposite player
        vertHorizWinBoard.nextTurn();
        assertTrue(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 4));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 4));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 4));
        vertHorizWinBoard.nextTurn();
        assertFalse(vertHorizWinBoard.getTurn());
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 3));
        assertEquals(1, vertHorizWinBoard.countNumPiecesCounterDiagonal(vertHorizWinBoard.getRecentlyPlaced().getPosX()
                , vertHorizWinBoard.getRecentlyPlaced().getPosY()));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 3));
        assertEquals(1, vertHorizWinBoard.countNumPiecesCounterDiagonal(vertHorizWinBoard.getRecentlyPlaced().getPosX()
                , vertHorizWinBoard.getRecentlyPlaced().getPosY()));
        assertTrue(vertHorizWinBoard.placePiece(COLUMNS - 3));
        assertEquals(1, vertHorizWinBoard.countNumPiecesCounterDiagonal(vertHorizWinBoard.getRecentlyPlaced().getPosX()
                , vertHorizWinBoard.getRecentlyPlaced().getPosY()));


        // Case for y < 0
        assertTrue(almostDrawBoard.placePiece(0));
        assertEquals(1, almostDrawBoard.countNumPiecesCounterDiagonal(almostDrawBoard.getRecentlyPlaced().getPosX()
                , almostDrawBoard.getRecentlyPlaced().getPosY()));

        almostDrawBoard.getBoard()[0][1] = null;
        assertTrue(almostDrawBoard.placePiece(1));
        assertEquals(2, almostDrawBoard.countNumPiecesCounterDiagonal(almostDrawBoard.getRecentlyPlaced().getPosX()
                , almostDrawBoard.getRecentlyPlaced().getPosY()));

    }

    @Test
    public void checkDrawTest() {
        assertFalse(emptyBoard.checkDraw());
        assertFalse(diagonalWinBoard.checkDraw());
        assertFalse(vertHorizWinBoard.checkDraw());
        assertFalse(almostDrawBoard.checkDraw());

        almostDrawBoard.nextTurn();
        assertTrue(almostDrawBoard.getTurn());
        assertTrue(almostDrawBoard.placePiece(0));
        assertTrue(almostDrawBoard.checkDraw());
    }

    // NOTE: only for testing purposes, copied from Connect4Console class
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

    // NOTE: only for testing purposes
    // EFFECTS: returns the piece ("O" or "X") based off of player's turn
    public String getPieceUI(Boolean player) {
        if (player) {
            return P2_PIECE;
        }
        return P1_PIECE;
    }

}