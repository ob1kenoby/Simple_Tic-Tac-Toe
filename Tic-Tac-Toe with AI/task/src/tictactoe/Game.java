package tictactoe;

import java.util.Random;
import java.util.Scanner;

class Game {
    private Player playerX;
    private Player playerO;
    private Board board;
    final private boolean launchNewGame;
    final private Scanner SCANNER = new Scanner(System.in);

    public Game() {
        this.launchNewGame = requestConfigFromUser();
        if (this.launchNewGame) {
            this.board = new Board(Board.generateEmptyBoard());
        }
    }

    public boolean gameIsOn() {
        return board.getMovesRemain() > 0;
    }

    /**
     * @return true if user selected to launch next game.
     */
    boolean isStarted() {
        return this.launchNewGame;
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
                if (commandAsArray.length >= 3 & "start".equals(commandAsArray[0].toLowerCase())) {
                    incorrectInput = false;
                    playerX = new Player(commandAsArray[1]);
                    playerO = new Player(commandAsArray[2]);
                    if (playerX.getMODE() == null || playerO.getMODE() == null) {
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

    void nextMove() {
        Player currentPlayer = board.getCurrentMove() == 'X' ? playerX : playerO;
        currentPlayer.printMove();
        switch (currentPlayer.getMODE()) {
            case USER:
                playerMove();
                break;
            case EASY:
                easyAIMove();
                break;
            case MEDIUM:
                mediumAIMove();
                break;
            case HARD:
                hardAIMove();
                break;
        }
        board.printBoard();
        if (board.isWin(board.getCurrentMove())) {
            System.out.printf("%s wins%n", board.getCurrentMove());;
        } else if (!gameIsOn()) {
            System.out.println("Draw");
        }
        board.passMoveToAnotherPlayer();
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
                if (!board.isBoxEmpty(x, y)) {
                    incorrectInput = true;
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
        } while (incorrectInput);
        board.makeMove(x, y);
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
            madeMove = board.makeMove(x, y);
            }
        }

    private void mediumAIMove() {
//        boolean canWin = testMoveForWin(false);
//        if (!canWin) {
//            boolean canPrevent = testMoveForWin(true);
//            if (!canPrevent) {
//                easyAIMove();
//            }
//        }
        easyAIMove();
    }

    private void hardAIMove() {
        Board newBoard = new Board(board.copyBoard());
        int score = minimax(newBoard, true);
    }

    private int minimax(Board newBoard, boolean isOwn) {
        if (newBoard.isWin(board.getCurrentMove())) {
            if (isOwn) {
                return 10;
            } else {
                return -10;
            }
        } else if (newBoard.getMovesRemain() == 0) {
            return 0;
        }

        return 0;
    }

    /**
     * Goes through the whole gaming field and checks if an opponent can place a symbol on a winning combinations or
     * the player that makes current move has winning combinations.
     * @param opposite is true if the current check is to prevent the opposite player from winning;
     *                 is false if checking for winning combinations for the current move.
     * @return true if can place such symbol on a board.
     */
//    private boolean testMoveForWin(boolean opposite) {
//        char symbol;
//        if (opposite) {
//            symbol = (board.getCurrentMove()) ? 'O' : 'X';
//        } else {
//            symbol = (board.getCurrentMove()) ? 'X' : 'O';
//        }
//        boolean canPreventOrWin = false;
//        outerLoop:
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board.isBoxEmpty(i, j)) {
//                    canPreventOrWin = board.testMove(i, j, symbol, opposite);
//                }
//                if (canPreventOrWin) {
//                    break outerLoop;
//                }
//            }
//        }
//        return canPreventOrWin;
//    }
}
