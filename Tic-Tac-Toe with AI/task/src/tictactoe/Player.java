package tictactoe;

class Player {
    final private Mode MODE;

    public Player(String difficulty) {
        this.MODE = Mode.findModeByDifficulty(difficulty);
    }

    Mode getMODE() {
        return MODE;
    }
}
