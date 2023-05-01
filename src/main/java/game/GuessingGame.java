package game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

public class GuessingGame {
    private String targetWord;
    private String maskedWord;
    private final Random random = new Random();
    private List<String> previousMaskedWords = new ArrayList<>();

    public GuessingGame(String targetWord) {
        this.targetWord = targetWord.toUpperCase();
        initializeMaskedWord();
    }

    public GuessingGame() {
        List<String> words = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get("words.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                words.add(jsonArray.getString(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        targetWord = words.get(random.nextInt(words.size())).toUpperCase();
        initializeMaskedWord();
    }

    private void initializeMaskedWord() {
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

        previousMaskedWords.add(maskedWord);
    }

    public boolean tryGuess(String guess) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);
        boolean isGuessCorrect = false;
        for (int i = 0; i < targetWord.length(); i++) {
            char targetChar = targetWord.toLowerCase().charAt(i);
            char guessChar = guess.toLowerCase().charAt(i);

            if (targetChar == guessChar && maskedWord.charAt(i) == '-') {
                updatedMaskedWord.setCharAt(i, targetChar);
                isGuessCorrect = true;
            }
        }

        if (isGuessCorrect) {
            maskedWord = updatedMaskedWord.toString();
            previousMaskedWords.add(maskedWord);
            return true;
        } else {
            return false;
        }
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

    public List<String> getPreviousMaskedWords() {
        return previousMaskedWords;
    }

}
