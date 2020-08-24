package tictactoe;
import java.util.Scanner;


public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[][] field = generateField();
        printField(field);
        boolean gameNotFinished = true;
        int step = 0;
        while (gameNotFinished) {
            step++;
            int[] coordinates;
            coordinates = getNewCoordinates(field);
            updateField(field, coordinates, step);
            printField(field);
            gameNotFinished = checkWinners(field);
        }
    }

    private static void updateField(String[][] field, int[] coordinates, int step) {
        String box;
        int x = coordinates[0];
        int y = coordinates[1];
        if (step % 2 == 1) {
            box = "X";
        } else {
            box = "O";
        }
        field[x][y] = box;
    }

    private static int[] getNewCoordinates(String[][] field) {
        int[] coordinates = new int[2];
        boolean incorrectInput = true;
        int x = 0;
        int y = 0;
        while (incorrectInput) {
            incorrectInput = false;
            System.out.print("Enter the coordinates: ");
            String rawCoordinates = scanner.nextLine();
            int i = 0;
            for (String digit : rawCoordinates.split(" ")) {
                try {
                    coordinates[i] = Integer.parseInt(digit);
                } catch (NumberFormatException numberFormatException) {
                    incorrectInput = true;
                    System.out.println("You should enter numbers!");
                    break;
                }
                if (coordinates[i] < 1 || coordinates[i] > 3) {
                    incorrectInput = true;
                    System.out.println("Coordinates should be from 1 to 3!");
                    break;
                }
                i++;
            }
            if (!incorrectInput) {
                x = 3 - coordinates[1];
                y = coordinates[0] - 1;
                String box = field[x][y];
                if ("X".equals(box) || "O".equals(box)) {
                    incorrectInput = true;
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
        }
        return new int[] {x, y};
    }

    private static boolean checkWinners(String[][] field) {
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
//            System.out.println(Arrays.toString(new String[]{field[0][i], field[1][i], field[2][i]}) + " i = " + i);
            oWinCount += checkDirect(new String[]{field[0][i], field[1][i], field[2][i]}, "O");
//            System.out.println("i = " + i + ", oWinCount = " + oWinCount);
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

    private static int checkDirect(String[] strings, String x) {
        for (String symbol : strings) {
            if (!x.equals(symbol)) {
                return 0;
            }
        }
        return 1;
    }

    private static String[][] generateField() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = " ";
            }
        }
        return field;
    }

    private static void printField(String[][] field) {
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
}
