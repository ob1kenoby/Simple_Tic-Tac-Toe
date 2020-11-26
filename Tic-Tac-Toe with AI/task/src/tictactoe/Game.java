package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private int playerX; // 0 - user, 1 - easy
    private int playerO;
    private Field field;
    private boolean gameIsOn;
    private int moveNumber;

    public Game() {
        this.gameIsOn = requestConfigFromUser();
        if (this.gameIsOn) {
            this.field = new Field();
            this.moveNumber = 0;
        }
    }

    boolean gameIsOn() {
        return this.gameIsOn;
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
            String command = getUserInput();
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

    private String getUserInput() {
        String userInput;
        try (Scanner scanner = new Scanner(System.in)) {
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    private int setDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "user": return 0;
            case "easy": return 1;
            case "medium": return 2;
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
                easyAIMove();
                break;
        }
        printField(field);
        gameNotFinished = checkWinners(field);
    }

    boolean isMoveOfX() {
        return (moveNumber % 2) == 1;
    }

    private void playerMove() {
        boolean incorrectInput = false;
        int x = 0;
        int y = 0;
        do {
            System.out.print("Enter the coordinates: ");
            String[] userInput = getUserInput().split("\\s");
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
            field.placeMoveOnField(x, y, "X");
        } else {
            field.placeMoveOnField(x, y, "O");
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
        System.out.println("Making move level \"easy\"");
    }
}
