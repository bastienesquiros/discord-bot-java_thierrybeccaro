package game;

import java.util.Vector;
import java.util.Random;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RandomWord extends ListenerAdapter {

    private final Vector<String> words = new Vector<String>();
    private final Random random = new Random();

    public RandomWord() {
        // FETCH UNE API
        words.add("TEST");
        words.add("THIBOL");
        words.add("JEANNE");
        words.add("PETEUR");
        words.add("MAMADOU");
    }

    public Pair<String, String> getTargetAndMaskedWord() {
        String word = words.get(random.nextInt(words.size()));
        int wordLength = word.length();

        // Sélectionner deux indices aléatoires distincts
        int firstIndex = random.nextInt(wordLength);
        int secondIndex = random.nextInt(wordLength);
        while (secondIndex == firstIndex) {
            secondIndex = random.nextInt(wordLength);
        }

        StringBuilder maskedWord = new StringBuilder();

        if (wordLength < 6) {
            for (int i = 0; i < wordLength; i++) {
                if (i == firstIndex) {
                    maskedWord.append(word.charAt(i));
                } else {
                    maskedWord.append('-');
                }
            }
        } else {
            for (int i = 0; i < wordLength; i++) {
                if (i == firstIndex || i == secondIndex) {
                    maskedWord.append(word.charAt(i));
                } else {
                    maskedWord.append('-');
                }
            }
        }

        // Construire le mot masqué avec des tirets

        return new Pair<>(word, maskedWord.toString());
    }
}
