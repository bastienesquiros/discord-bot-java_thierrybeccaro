package game;

import java.util.Vector;
import java.util.Random;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RandomWord extends ListenerAdapter {

    private final Vector<String> words = new Vector<String>();
    private final Random random = new Random();

    public RandomWord() {
        // FETCH UNE API
        words.add("THIBOL");
        words.add("JEANNE");
        words.add("PETEUR");
        words.add("MAMADOU");
    }

    public String getRandomWord() {
        String word = words.get(random.nextInt(words.size()));
        int wordLength = word.length();

        // Sélectionner deux indices aléatoires distincts
        int firstIndex = random.nextInt(wordLength);
        int secondIndex = random.nextInt(wordLength);
        while (secondIndex == firstIndex) {
            secondIndex = random.nextInt(wordLength);
        }

        // Construire le mot masqué avec des tirets
        StringBuilder maskedWord = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            if (i == firstIndex || i == secondIndex) {
                maskedWord.append(word.charAt(i));
            } else {
                maskedWord.append('-');
            }
        }

        return maskedWord.toString();
    }

    // public boolean isMatching() {
    // }
}