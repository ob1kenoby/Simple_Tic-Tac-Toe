package tictactoe;

enum Mode {
    USER("user"),
    EASY("easy"),       // Random move.
    MEDIUM("medium"),   // Attempts to win or prevent winning in one move. Random move otherwise.
    HARD("hard");       // Minimax algorithm.

    final private String DIFFICULTY;

    Mode(String difficulty) {
        this.DIFFICULTY = difficulty;
    }

    static Mode findModeByDifficulty(String difficulty) {
        for (Mode mode: values()) {
            if (mode.DIFFICULTY.equals(difficulty.toLowerCase())) {
                return mode;
            }
        }
        return null;
    }

    String getDifficulty() {
        return DIFFICULTY;
    }
}
