package game;

public class GuessWord {
    private String targetWord;
    private String maskedWord;

    public GuessWord(String targetWord, String maskedWord) {
        this.targetWord = targetWord;
        this.maskedWord = maskedWord;
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
            char targetChar = targetWord.toLowerCase().charAt(i);
            char userChar = userWord.toLowerCase().charAt(i);

            if (targetChar == userChar && maskedWord.charAt(i) == '-') {
                updatedMaskedWord.setCharAt(i, targetChar);
            }
        }

        maskedWord = updatedMaskedWord.toString();
    }
}
