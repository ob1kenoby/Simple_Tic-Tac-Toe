package tictactoe;

class Player {
    final private Mode MODE;  // Difficulty or user control.

    public Player(String difficulty) {
        this.MODE = Mode.findModeByDifficulty(difficulty);
    }

    Mode getMODE() {
        return MODE;
    }

    void printMove() {
        if (this.MODE != Mode.USER) {
            System.out.printf("Making move level \"%s\"%n", MODE.getDifficulty());
        }
    }
}
