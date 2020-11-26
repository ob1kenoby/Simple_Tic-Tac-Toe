package tictactoe;

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
        int x;
        int y;
        do {
            System.out.print("Enter the coordinates: ");
            String[] userInput = getUserInput().split("\\s");
            if (userInput.length == 2) {
                x = getCoordinateFromInput(userInput[0]);
                y = getCoordinateFromInput(userInput[1]);
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
        } while (incorrectInput);
    }

    private int getCoordinateFromInput(String s) throws NumberFormatException {
    }
}
