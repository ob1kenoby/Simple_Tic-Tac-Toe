package tictactoe;

import java.util.Arrays;

class Board {
    final private char[][] BOARD;
    private int movesRemain = 9;
    private char currentMove = 'X';

    public Board(char[][] BOARD) {
        this.BOARD = Arrays.copyOf(BOARD, 3);
        printBoard();
    }

    static char[][] generateEmptyBoard() {
        char[][] field = new char[3][3];
        for (char[] chars : field) {
            Arrays.fill(chars, ' ');
        }
        return field;
    }

    char[][] copyBoard() {
        return Arrays.copyOf(BOARD, 3);
    }

    int getMovesRemain() {
        return movesRemain;
    }

    void passMoveToAnotherPlayer() {
        currentMove = currentMove == 'X' ? 'O' : 'X';
    }

    boolean isBoxEmpty(int x, int y) {
        return ' ' == BOARD[x][y];
    }

    boolean makeMove(int x, int y) {
        if (isBoxEmpty(x, y)) {
            BOARD[x][y] = currentMove;
            movesRemain--;
            return true;
        }
        return false;
    }

    public boolean isWin(char symbol) {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (!win) {
                win = checkDirect(BOARD[i], symbol);
                if (!win) {
                    win = checkDirect(new char[]{BOARD[0][i], BOARD[1][i], BOARD[2][i]}, symbol);
                }
            }
        }
        if (!win) {
            win = checkDiagonals(symbol);
        }
        if (win) {
            movesRemain = 0;
        }
        return win;
    }

    private static boolean checkDirect(char[] strings, char symbol) {
        for (char spot : strings) {
            if (symbol != spot) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonals(char symbol) {
        int countLeft = 0;
        int countRight = 0;
        for (int i = 0; i < 3; i++) {
            if (symbol == BOARD[i][i]) {
                countLeft++;
            }
            if (symbol == BOARD[2-i][i]) {
                countRight++;
            }
        }
        return countLeft == 3 || countRight == 3;
    }

    char getCurrentMove() {
        return currentMove;
    }

    void printBoard() {
        System.out.println("---------");
        for (char[] row : BOARD) {
            System.out.print("| ");
            for (char symbol : row) {
                System.out.print(symbol + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

//    boolean testMove(int x, int y, char symbol, boolean opposite) {
//        makeMove(x, y, symbol);
//        boolean canWin = checkIfWinner();
//        if (!canWin) {
//            movesRemain++;
//            BOARD[x][y] = ' ';
//        } else if (opposite) {
//            BOARD[x][y] = 'X' == symbol ? 'O' : 'X';
//        }
//        return canWin;
//    }
}
