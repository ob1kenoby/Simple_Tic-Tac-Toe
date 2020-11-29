package tictactoe;

public class Main {
    public static void main(String[] args) {
        boolean launchNewGame = true;
        while (launchNewGame) {
            Game game = new Game();
            while (game.gameIsOn()) {
                game.nextMove();
            }
            launchNewGame = false;  // game.isStarted();
        }
    }
}