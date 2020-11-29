package tictactoe;

enum Mode {
    USER("user"),
    EASY("easy"),       // Random move.
    MEDIUM("medium"),   // Random move if not win or lose in one move. Then tries to win or lose in one move.
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
