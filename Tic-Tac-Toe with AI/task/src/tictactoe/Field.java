package tictactoe;

public class Field {
    final private String[][] field = new String[3][3];

    public Field() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.field[i][j] = " ";
            }
        }
        printField();
    }

    void printField() {
        System.out.println("---------");
        for (String[] row : field) {
            System.out.print("| ");
            for (String symbol : row) {
                System.out.print(symbol + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    boolean isBoxEmpty(int x, int y) {
        return " ".equals(field[x][y]);
    }

    void placeMoveOnField(int x, int y, String symbol) {
        field[x][y] = symbol;
    }

    boolean checkWinners() {
        int xWinCount = 0;
        int oWinCount = 0;
        int spaceCount = 0;
        boolean gameNotFinished = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (" ".equals(field[i][j])) {
                    spaceCount += 1;
                }
            }
            xWinCount += checkDirect(field[i], "X");
            oWinCount += checkDirect(field[i], "O");
            xWinCount += checkDirect(new String[]{field[0][i], field[1][i], field[2][i]}, "X");
            oWinCount += checkDirect(new String[]{field[0][i], field[1][i], field[2][i]}, "O");
        }
        xWinCount += checkDiagonals(field, "X");
        oWinCount += checkDiagonals(field, "O");
        if (xWinCount > 0) {
            System.out.println("X wins");
            gameNotFinished = false;
        } else if (oWinCount > 0) {
            System.out.println("O wins");
            gameNotFinished = false;
        } else if (spaceCount == 0) {
            System.out.println("Draw");
            gameNotFinished = false;
        }
        return gameNotFinished;
    }

    private static int checkDirect(String[] strings, String x) {
        for (String symbol : strings) {
            if (!x.equals(symbol)) {
                return 0;
            }
        }
        return 1;
    }

    private static int checkDiagonals(String[][] field, String x) {
        int victory = 0;
        int countLeft = 0;
        int countRight = 0;
        for (int i = 0; i < 3; i++) {
            if (x.equals(field[i][i])) {
                countLeft++;
            }
            if (x.equals(field[2-i][i])) {
                countRight++;
            }
        }
        victory = (countLeft == 3) ? victory + 1 : victory;
        victory = (countRight == 3) ? victory + 1 : victory;
        return victory;
    }
}
