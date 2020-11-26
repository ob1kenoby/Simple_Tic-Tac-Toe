package tictactoe;

public class Field {
    private String[][] field = new String[3][3];

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
}
