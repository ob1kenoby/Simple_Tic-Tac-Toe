import java.util.Arrays;

class Board {
    private final char[][] board = new char[3][3];
    private int movesRemain;

    /**
     * Creation of a copy of the parameter array is needed to preserve the original board, while medium or hard AI
     * iterates through possible moves and modifies the test boards.
     * @param board is empty (filled with ' ') when the game starts.
     */
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

    /**
     * For the start of the game.
     * @return empty board.
     */
    static char[][] generateEmptyBoard() {
        char[][] field = new char[3][3];
        for (char[] chars : field) {
            Arrays.fill(chars, ' ');
        }
        return field;
    }

    /**
     * Copy of the board is needed to create a new Board object on which medium and hard AI checks their scenarios.
     * @return a copy of the current board.
     */
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
            char symbol = getCurrentMove();
            board[x][y] = symbol;
            movesRemain--;
            return true;
        }
        return false;
    }

    public boolean isWin() {
        boolean winX = checkIfSymbolWon('X');
        boolean winO = checkIfSymbolWon('O');
        return winX || winO;
    }

    private boolean checkIfSymbolWon(char symbol) {
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
            movesRemain = 0; // Stops the game after victory.
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

    /**
     * This method is required for medium AI, to test if the opponent can win by occupying the cell, which coordinates
     * are passed as the parameters.
     */
    void testOppositeMove(int x, int y) {
        char symbol = getCurrentMove() == 'X' ? 'O' : 'X';
        board[x][y] = symbol;
        movesRemain--;
    }
}
