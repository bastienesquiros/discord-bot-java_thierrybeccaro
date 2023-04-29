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

    public String getRandomWord() {
        String word = words.get(random.nextInt(words.size()));
        int wordLength = word.length();

        int firstIndex = random.nextInt(wordLength);
        int secondIndex = random.nextInt(wordLength);
        while (secondIndex == firstIndex) {
            secondIndex = random.nextInt(wordLength);
        }

        StringBuilder maskedWord = new StringBuilder(wordLength);

        for (int i = 0; i < wordLength; i++) {
            char c = '-';
            if (i == firstIndex || (i == secondIndex && wordLength >= 6)) {
                c = word.charAt(i);
            }
            maskedWord.append(c);
        }

        return maskedWord.toString();
    }

    // public boolean isMatching(){}
}