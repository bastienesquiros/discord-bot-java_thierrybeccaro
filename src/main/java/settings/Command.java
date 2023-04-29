package settings;

import game.ChooseWord;
import game.RandomWord;
import game.GuessWord;
import game.Pair;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {
    private final RandomWord randomWord = new RandomWord();
    private GuessWord guessWord;
    private ChooseWord chooseWord;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("jouer")) {
            if (event.getOption("mot") != null) {
                String word = event.getOption("mot").getAsString();
                chooseWord = new ChooseWord(word.toUpperCase());
                event.reply("Devinez le mot : " + chooseWord.getMaskedWord()).queue();
            } else {
                Pair<String, String> wordPair = randomWord.getTargetAndMaskedWord();
                String targetWord = wordPair.getKey();
                String maskedWord = wordPair.getValue();
                event.reply("Devinez le mot : " + maskedWord).queue();
                guessWord = new GuessWord(targetWord, maskedWord);
            }
        } else if (event.getName().equals("deviner")) {
            if (chooseWord == null && guessWord == null) {
                event.reply("Veuillez d'abord lancer une partie avec la commande /jouer").queue();
                return;
            }

            String userWord = event.getOption("mot").getAsString().toUpperCase();
            if (guessWord != null) {
                guessWord.tryGuess(userWord);
                String updatedMaskedWord = guessWord.getMaskedWord();

                if (guessWord.isWordComplete()) {
                    event.reply("Bravo ! Vous avez trouvé le mot : " + updatedMaskedWord).queue();
                    guessWord = null;
                } else {
                    event.reply("Mot mis à jour : " + updatedMaskedWord).queue();
                }
            } else {
                chooseWord.tryGuess(userWord);
                String updatedMaskedWord = chooseWord.getMaskedWord();

                if (chooseWord.isWordComplete()) {
                    event.reply("Bravo ! Vous avez trouvé le mot : " + updatedMaskedWord).queue();
                    chooseWord = null;
                } else {
                    event.reply("Mot mis à jour : " + updatedMaskedWord).queue();
                }
            }
        } else if (event.getName().equals("reset")) {
            guessWord = null;
            chooseWord = null;
            event.reply("La partie en cours a été annulée.").queue();
        }
    }
}
