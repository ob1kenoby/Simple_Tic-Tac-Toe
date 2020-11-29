package tictactoe;

import java.util.Arrays;

class Board {
    final private char[][] BOARD;
    int movesRemain = 9;
    char winner;

    public Board(char[][] BOARD) {
        this.BOARD = Arrays.copyOf(BOARD, 3);
        printBoard();
    }

    public static char[][] generateEmptyBoard() {
        char[][] field = new char[3][3];
        for (char[] chars : field) {
            Arrays.fill(chars, ' ');
        }
        return field;
    }

    public char[][] getBOARD() {
        return Arrays.copyOf(BOARD, 3);
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

    boolean isBoxEmpty(int x, int y) {
        return ' ' == BOARD[x][y];
    }

    void placeMoveOnField(int x, int y, char symbol) {
        BOARD[x][y] = symbol;
        movesRemain--;
    }

    boolean checkIfWinner() {
        int xWinCount = 0;
        int oWinCount = 0;
        for (int i = 0; i < 3; i++) {
            xWinCount += checkDirect(BOARD[i], 'X');
            oWinCount += checkDirect(BOARD[i], 'O');
            xWinCount += checkDirect(new char[]{BOARD[0][i], BOARD[1][i], BOARD[2][i]}, 'X');
            oWinCount += checkDirect(new char[]{BOARD[0][i], BOARD[1][i], BOARD[2][i]}, 'O');
        }
        xWinCount += checkDiagonals(BOARD, 'X');
        oWinCount += checkDiagonals(BOARD, 'O');
        if (xWinCount > 0 || oWinCount > 0) {
            this.winner = (xWinCount > 0) ? 'X' : 'O';
            return true;
        }
        return false;
    }

    private static int checkDirect(char[] strings, char x) {
        for (char symbol : strings) {
            if (x != symbol) {
                return 0;
            }
        }
        return 1;
    }

    private static int checkDiagonals(char[][] field, char x) {
        int victory = 0;
        int countLeft = 0;
        int countRight = 0;
        for (int i = 0; i < 3; i++) {
            if (x == field[i][i]) {
                countLeft++;
            }
            if (x == field[2-i][i]) {
                countRight++;
            }
        }
        victory = (countLeft == 3) ? victory + 1 : victory;
        victory = (countRight == 3) ? victory + 1 : victory;
        return victory;
    }

    boolean testMove(int x, int y, char symbol, boolean opposite) {
        placeMoveOnField(x, y, symbol);
        boolean canWin = checkIfWinner();
        if (!canWin) {
            movesRemain++;
            BOARD[x][y] = ' ';
        } else if (opposite) {
            BOARD[x][y] = 'X' == symbol ? 'O' : 'X';
        }
        return canWin;
    }

    public boolean canContinue() {
        if (checkIfWinner()) {
            System.out.printf("%s wins%n", winner);
        } else if (movesRemain == 0) {
            System.out.println("Draw");
        } else {
            return true;
        }
        return false;
    }

    boolean isMoveOfX() {
        return (movesRemain % 2) == 0;
    }
}
