package tictactoe;

enum Mode {
    USER("user"),
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    String difficulty;

    Mode(String difficulty) {
        this.difficulty = difficulty;
    }

    public static Mode findModeByDifficulty(String difficulty) {
        for (Mode mode: values()) {
            if (mode.difficulty.equals(difficulty.toLowerCase())) {
                return mode;
            }
        }
        return null;
    }
}
