package settings;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;

public class Initialization {

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA bot = JDABuilder.createDefault("MTEwMTU4MTU3ODAwNzc2MDkzNg.GRa2zD._anOnNiXr6Gv-wGUZspZOEEy273LGuVyMAX9uo")
                .setActivity(Activity.playing("MOTUS"))
                .addEventListeners(new Command())
                .build();

        Guild guild = bot.getGuildById("1101585066087415948");

        if (guild != null) {
            guild.upsertCommand("fart", "Fart really hard").queue();
        }

    }
}