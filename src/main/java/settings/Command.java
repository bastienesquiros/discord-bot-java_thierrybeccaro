package settings;

import game.RandomWord;
import game.GuessWord;
import game.Pair;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {
    private final RandomWord randomWord = new RandomWord();
    private GuessWord guessWord;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("jouer")) {
            Pair<String, String> wordPair = randomWord.getTargetAndMaskedWord();
            String targetWord = wordPair.getKey();
            String maskedWord = wordPair.getValue();
            event.reply("Devinez le mot : " + maskedWord).queue();
            guessWord = new GuessWord(targetWord, maskedWord);
        } else if (event.getName().equals("deviner")) {
            if (guessWord == null) {
                event.reply("Veuillez d'abord lancer une partie avec la commande /jouer").queue();
                return;
            }
            String userWord = event.getOption("mot").getAsString();
            guessWord.tryGuess(userWord);
            String updatedMaskedWord = guessWord.getMaskedWord();

            if (guessWord.isWordComplete()) {
                event.reply("Bravo ! Vous avez trouvé le mot : " + updatedMaskedWord).queue();
                guessWord = null;
            } else {
                event.reply("Mot mis à jour : " + updatedMaskedWord).queue();
            }
        }
    }
}
