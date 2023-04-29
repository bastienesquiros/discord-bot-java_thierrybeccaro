package game;

public class ChooseWord {
    private String targetWord;
    private String maskedWord;

    public ChooseWord(String targetWord) {
        this.targetWord = targetWord.toUpperCase();
        this.maskedWord = "-".repeat(targetWord.length());
    }

    public String getMaskedWord() {
        return maskedWord;
    }

    public boolean isWordComplete() {
        return !maskedWord.contains("-");
    }

    public void tryGuess(String userWord) {
        if (userWord == null || userWord.length() != targetWord.length()) {
            return;
        }

        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);
        for (int i = 0; i < targetWord.length(); i++) {
            char targetChar = targetWord.charAt(i);
            char userChar = userWord.charAt(i);

            if (targetChar == userChar && maskedWord.charAt(i) == '-') {
                updatedMaskedWord.setCharAt(i, targetChar);
            }
        }

        maskedWord = updatedMaskedWord.toString();
    }
}
