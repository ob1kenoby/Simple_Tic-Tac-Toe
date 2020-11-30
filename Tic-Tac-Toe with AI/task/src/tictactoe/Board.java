package tictactoe;

import java.util.Arrays;

class Board {
    private final char[][] board = new char[3][3];
    private int movesRemain;

    public Board(char[][] board) {
        this.movesRemain = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
                if (board[i][j] == ' ') {
                    movesRemain++;
                }
            }
        }
    }

    static char[][] generateEmptyBoard() {
        char[][] field = new char[3][3];
        for (char[] chars : field) {
            Arrays.fill(chars, ' ');
        }
        return field;
    }

    char[][] copyBoard() {
        return Arrays.copyOf(board, 3);
    }

    int getMovesRemain() {
        return movesRemain;
    }

    boolean isBoxEmpty(int x, int y) {
        return ' ' == board[x][y];
    }

    boolean makeMove(int x, int y) {
        if (isBoxEmpty(x, y)) {
            board[x][y] = getCurrentMove();
            movesRemain--;
            return true;
        }
        return false;
    }

    public boolean isWin(char nextMove) {
        char symbol = nextMove == 'O' ? 'X' : 'O';
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (!win) {
                win = checkDirect(board[i], symbol);
                if (!win) {
                    win = checkDirect(new char[]{board[0][i], board[1][i], board[2][i]}, symbol);
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
            if (symbol == board[i][i]) {
                countLeft++;
            }
            if (symbol == board[2-i][i]) {
                countRight++;
            }
        }
        return countLeft == 3 || countRight == 3;
    }

    char getCurrentMove() {
        return movesRemain % 2 == 1 ? 'X' : 'O';
    }

    void printBoard() {
        System.out.println("---------");
        for (char[] row : board) {
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
