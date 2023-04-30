package settings;

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

        event.deferReply().complete().sendMessage("Devinez le mot suivant: " + game.getMaskedWord().toUpperCase())
                .queue();
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

        if (game.isWordComplete()) {
            event.deferReply().complete().sendMessage("Bravo! Vous avez deviné: " + game.getMaskedWord().toUpperCase())
                    .queue();
            isPlaying = false;
        } else {
            event.deferReply().complete().sendMessage("Devinez le mot: " + game.getMaskedWord().toUpperCase()).queue();
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
}
