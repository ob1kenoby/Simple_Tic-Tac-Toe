package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private int playerX; // 0 - user, 1 - easy
    private int playerO;
    private Field field;

    public boolean gameIsOn() {
        return gameIsOn;
    }

    private boolean gameIsOn;
    final private boolean gameStarted;
    private int moveNumber;
    final private Scanner SCANNER = new Scanner(System.in);

    public Game() {
        this.gameStarted = requestConfigFromUser();
        this.gameIsOn = this.gameStarted;
        if (this.gameStarted) {
            this.field = new Field(Field.generateEmptyField());
            this.moveNumber = 0;
        }
    }

    boolean isStarted() {
        return this.gameStarted;
    }

    /**
     * Prompts user to start a game or exit. If the user chooses to start a game, they must specify difficulty by typing
     * "start difficulty difficulty" where first difficulty sets game mode for X player and the second for O player.
     * Possible values for difficulty are:
     *      user - user controlled player
     *      easy - easy "AI"
     * @return true if the parameters are correct and the game starts
     *         false if user chooses to exit
     */
    private boolean requestConfigFromUser() {
        System.out.print("Input command: ");
        boolean incorrectInput = true;
        while (incorrectInput) {
            String command = SCANNER.nextLine();
            if ("exit".equals(command.toLowerCase())) {
                return false;
            } else {
                String[] commandAsArray = command.split("\\s");
                if (commandAsArray.length == 3 & "start".equals(commandAsArray[0].toLowerCase())) {
                    incorrectInput = false;
                    playerX = setDifficulty(commandAsArray[1]);
                    playerO = setDifficulty(commandAsArray[2]);
                    if (playerX < 0 || playerO < 0) {
                        incorrectInput = true;
                    }
                }
            }
            if (incorrectInput) {
                System.out.println("Bad parameters!");
            }
        }
        return true;
    }

    private int setDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "user": return 0;
            case "easy": return 1;
            case "medium": return 2;
            case "hard": return 3;
            default: return -1;
        }
    }

    void nextMove() {
        moveNumber++;
        int currentPlayer;
        if (isMoveOfX()) {
            currentPlayer = playerX;
        } else {
            currentPlayer = playerO;
        }
        switch (currentPlayer) {
            case 0:
                playerMove();
                break;
            case 1:
                System.out.println("Making move level \"easy\"");
                easyAIMove();
                break;
            case 2:
                System.out.println("Making move level \"medium\"");
                mediumAIMove();
                break;
            case 3:
                System.out.println("Making move level \"hard\"");
                hardAIMove();
                break;
        }
        field.printField();
        gameIsOn = field.canContinue();
    }

    boolean isMoveOfX() {
        return (moveNumber % 2) == 1;
    }

    private void playerMove() {
        boolean incorrectInput;
        int x = 0;
        int y = 0;
        do {
            incorrectInput = false;
            System.out.print("Enter the coordinates: ");
            String[] userInput = SCANNER.nextLine().split("\\s");
            if (userInput.length == 2) {
                try {
                    x = getCoordinateFromInput(userInput[0]);
                    y = getCoordinateFromInput(userInput[1]);
                } catch (NumberFormatException numberFormatException) {
                    incorrectInput = true;
                    System.out.println("You should enter numbers!");
                }
                if (!incorrectInput && (x < 0 || x > 2 || y < 0 || y > 2)) {
                    incorrectInput = true;
                    System.out.println("Coordinates should be from 1 to 3!");
                }
            } else {
                incorrectInput = true;
                System.out.println("Please enter two digits.");
            }
            if (!incorrectInput) {
                if (!field.isBoxEmpty(x, y)) {
                    incorrectInput = true;
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
        } while (incorrectInput);
        makeAMove(x, y);
    }

    private void makeAMove(int x, int y) {
        if (isMoveOfX()) {
            field.placeMoveOnField(x, y, 'X');
        } else {
            field.placeMoveOnField(x, y, 'O');
        }
    }

    private int getCoordinateFromInput(String coordinate) throws NumberFormatException {
        return Integer.parseInt(coordinate) - 1;
    }

    private void easyAIMove() {
        boolean madeMove = false;
        while (!madeMove) {
            Random random = new Random();
            int x = random.nextInt(3);
            int y = random.nextInt(3);
            if (field.isBoxEmpty(x, y)) {
                makeAMove(x, y);
                madeMove = true;
            }
        }
    }

    private void mediumAIMove() {
        boolean canWin = testMoveForWin(false);
        if (!canWin) {
            boolean canPrevent = testMoveForWin(true);
            if (!canPrevent) {
                easyAIMove();
            }
        }
    }

    private void hardAIMove() {
        boolean canWin = testMoveForWin(false);
        if (!canWin) {
            boolean canPrevent = testMoveForWin(true);
            if (!canPrevent) {
                easyAIMove();
            }
        }
    }

    /**
     * Goes through the whole gaming field and checks if an opponent can place a symbol on a winning combinations or
     * the player that makes current move has winning combinations.
     * @param opposite is true if the current check is to prevent the opposite player from winning;
     *                 is false if checking for winning combinations for the current move.
     * @return true if can place such symbol on a board.
     */
    private boolean testMoveForWin(boolean opposite) {
        char symbol;
        if (opposite) {
            symbol = (isMoveOfX()) ? 'O' : 'X';
        } else {
            symbol = (isMoveOfX()) ? 'X' : 'O';
        }
        boolean canPreventOrWin = false;
        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field.isBoxEmpty(i, j)) {
                    canPreventOrWin = field.testMove(i, j, symbol, opposite);
                }
                if (canPreventOrWin) {
                    break outerLoop;
                }
            }
        }
        return canPreventOrWin;
    }
}
