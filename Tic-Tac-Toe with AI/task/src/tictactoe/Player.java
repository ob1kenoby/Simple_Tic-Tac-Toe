package tictactoe;

class Player {
    final private Mode MODE;  // Difficulty or user control.
    final private char PLAYER;

    public Player(String difficulty, char symbol) {
        this.MODE = Mode.findModeByDifficulty(difficulty);
        this.PLAYER = symbol;
    }

    Mode getMODE() {
        return MODE;
    }

    public char getPLAYER() {
        return PLAYER;
    }

    void printMove() {
        if (this.MODE != Mode.USER) {
            System.out.printf("Making move level \"%s\"%n", MODE.getDifficulty());
        }
    }
}
