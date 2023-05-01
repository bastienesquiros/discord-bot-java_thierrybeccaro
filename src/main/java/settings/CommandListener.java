package settings;

import org.jetbrains.annotations.NotNull;
import java.util.List;

import game.GuessingGame;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    private GuessingGame game;
    private boolean isPlaying;

    public CommandListener() {
        this.game = null;
        this.isPlaying = false;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        switch (commandName) {
            case "jouer":
                jouer(event);
                break;
            case "deviner":
                deviner(event);
                break;
            case "reset":
                reset(event);
                break;
            case "stop":
                stop(event);
                break;

            default:
                break;
        }
    }

    public void jouer(SlashCommandInteractionEvent event) {
        if (isPlaying) {
            event.deferReply().complete()
                    .sendMessage("Une partie est déjà en cours ! Utilisez `/stop` pour la terminer.").queue();
            return;
        }

        String word = event.getOption("mot") != null ? event.getOption("mot").getAsString() : null;

        if (word != null && !word.isEmpty()) {
            game = new GuessingGame(word);
        } else {
            game = new GuessingGame();
        }

        isPlaying = true;

        event.deferReply().complete().sendMessage("Démarrage de la partie. Mo Mo Motus !").queue();

        event.getChannel().sendMessage("Entrez un mot de " + game.getMaskedWord().length() + " lettres").queue();

        String maskedWord = game.getMaskedWord().toUpperCase();
        event.getChannel().sendMessage(convertToRegionalIndicatorEmojis(maskedWord)).queue();
    }

    public void deviner(SlashCommandInteractionEvent event) {
        if (!isPlaying) {
            event.deferReply().complete()
                    .sendMessage(
                            "Il n'y a pas de partie en cours. Utilisez `/jouer` pour commencer une nouvelle partie.")
                    .queue();
            return;
        }

        String guess = event.getOption("mot") != null ? event.getOption("mot").getAsString() : null;
        if (guess == null || guess.length() != game.getMaskedWord().length()) {
            event.deferReply().complete().sendMessage(
                    "Erreur. Vous devez proposer des mots de " + game.getMaskedWord().length()
                            + " lettres uniquement. Réessayez.")
                    .queue();
            return;
        }

        game.tryGuess(guess);
        List<String> previousMaskedWords = game.getPreviousMaskedWords();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < previousMaskedWords.size() - 1; i++) { // Ignore the last masked word
            sb.append(convertToRegionalIndicatorEmojis(previousMaskedWords.get(i))).append("\n");
        }

        if (game.isWordComplete()) {
            event.deferReply().complete()
                    .sendMessage("Bravo, vous avez trouvé le mot !")
                    .queue();
            sb.append(convertToRegionalIndicatorEmojis(game.getMaskedWord())).append("\n");
            event.getChannel()
                    .sendMessage(sb.toString()).queue();
            isPlaying = false;
        } else {
            sb.append(convertToRegionalIndicatorEmojis(game.getMaskedWord())).append("\n");
            event.deferReply().complete()
                    .sendMessage(sb.toString())
                    .queue();
        }
    }

    public void stop(SlashCommandInteractionEvent event) {
        if (!isPlaying) {
            event.deferReply().complete()
                    .sendMessage("Pas de partie en cours. Utilisez `/jouer` pour commencer une nouvelle partie.")
                    .queue();
            return;
        }

        event.deferReply().complete().sendMessage("La partie est terminée. Le mot était: " + game.getTargetWord())
                .queue();
        isPlaying = false;
    }

    public void reset(SlashCommandInteractionEvent event) {
        game = null;
        isPlaying = false;
        event.deferReply().complete().sendMessage("Bip Bop... Thierry a été réinitialisé.").queue();
    }

    public String convertToRegionalIndicatorEmojis(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowerCaseChar = Character.toLowerCase(c);
                output.append(":regional_indicator_").append(lowerCaseChar).append(": ");
            } else {
                output.append(":red_square: ");
            }
        }
        return output.toString();
    }

}