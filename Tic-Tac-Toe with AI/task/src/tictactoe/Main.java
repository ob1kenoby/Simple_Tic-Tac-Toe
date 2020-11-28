package tictactoe;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        while (game.gameIsOn()) {
            game.nextMove();
        }
    }
}