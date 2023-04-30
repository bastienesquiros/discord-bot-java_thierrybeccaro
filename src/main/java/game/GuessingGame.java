package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuessingGame {

    private String targetWord;

    private String maskedWord;
    private final Random random = new Random();

    public GuessingGame(String targetWord) {
        this.targetWord = targetWord.toUpperCase();
        int wordLength = targetWord.length();
        int firstIndex = random.nextInt(wordLength);
        int secondIndex = (wordLength < 6) ? -1 : random.nextInt(wordLength);
        while (secondIndex == firstIndex) {
            secondIndex = random.nextInt(wordLength);
        }

        StringBuilder maskedWordBuilder = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            if (i == firstIndex || i == secondIndex) {
                maskedWordBuilder.append(targetWord.charAt(i));
            } else {
                maskedWordBuilder.append('-');
            }
        }
        maskedWord = maskedWordBuilder.toString();
    }

    public GuessingGame() {
        List<String> words = new ArrayList<>();
        // FETCH WORDS FROM API AND ADD TO THE LIST
        words.add("TEST");
        words.add("THIBOL");
        words.add("JEANNE");
        words.add("PETEUR");
        words.add("MAMADOU");

        targetWord = words.get(random.nextInt(words.size())).toUpperCase();
        int wordLength = targetWord.length();
        int firstIndex = random.nextInt(wordLength);
        int secondIndex = (wordLength < 6) ? -1 : random.nextInt(wordLength);
        while (secondIndex == firstIndex) {
            secondIndex = random.nextInt(wordLength);
        }

        StringBuilder maskedWordBuilder = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            if (i == firstIndex || i == secondIndex) {
                maskedWordBuilder.append(targetWord.charAt(i));
            } else {
                maskedWordBuilder.append('-');
            }
        }
        maskedWord = maskedWordBuilder.toString();
    }

    public String getMaskedWord() {
        return maskedWord;
    }

    public String getTargetWord() {
        return targetWord;
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
