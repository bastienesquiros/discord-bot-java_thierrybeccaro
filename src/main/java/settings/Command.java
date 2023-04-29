package settings;

import org.jetbrains.annotations.NotNull;
import game.RandomWord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {

    private final RandomWord randomWords = new RandomWord();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("jouer")) {
            String randomWord = randomWords.getRandomWord();
            event.reply("Devine moi ce mot " + randomWord).queue();
        }

    }
}