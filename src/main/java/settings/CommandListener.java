package settings;

import java.util.List;

import org.jetbrains.annotations.NotNull;

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
        event.getChannel().sendMessage(EmojiConverter.convertToRegionalIndicatorEmojis(maskedWord)).queue();
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
            event.getHook().sendMessage(
                    "Erreur. Vous devez proposer des mots de " + game.getMaskedWord().length()
                            + " lettres uniquement. Réessayez.")
                    .complete();
            return;
        }

        game.tryGuess(guess);
        List<String> previousMaskedWords = game.getPreviousMaskedWords();
        StringBuilder sb = new StringBuilder();
        for (String maskedWord : previousMaskedWords) {
            sb.append(convertToRegionalIndicatorEmojis(maskedWord)).append("\n");
        }

        if (game.isWordComplete()) {
            event.deferReply().complete()
                    .sendMessage("Bravo, vous avez trouvé le mot !")
                    .queue();
            event.getChannel()
                    .sendMessage(sb.toString()).queue();
            isPlaying = false;
        } else {
            event.deferReply().complete()
                    .sendMessage(convertToRegionalIndicatorEmojis(game.getMaskedWord()))
                    .queue();
            event.getChannel()
                    .sendMessage(sb.toString()).queue();
        }
    }

    public void stop(SlashCommandInteractionEvent event) {
        if (!isPlaying) {
            event.deferReply().complete()
                    .sendMessage("Pas de partie en cours. Utilisez `/jouer` pour commencer une nouvelle partie.")
                    .queue();
            return;
        }

        event.deferReply().complete()
                .sendMessage("La partie est terminée. Le mot était: "
                        + EmojiConverter.convertToRegionalIndicatorEmojis(game.getTargetWord()))
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
