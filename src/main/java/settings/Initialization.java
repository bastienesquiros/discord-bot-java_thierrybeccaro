package settings;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Initialization {

        public static void main(String[] args) throws LoginException, InterruptedException {
                JDA jda = JDABuilder.createDefault(
                                "MTEwMTU4MTU3ODAwNzc2MDkzNg.GRa2zD._anOnNiXr6Gv-wGUZspZOEEy273LGuVyMAX9uo")
                                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS,
                                                GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                                                GatewayIntent.MESSAGE_CONTENT)
                                .setActivity(Activity.playing("MOTUS"))
                                .addEventListeners(new CommandListener(), new MessageListener())
                                .build()
                                .awaitReady();

                jda.upsertCommand("jouer", "Lancez une partie de MOTUS")
                                .addOption(OptionType.STRING, "mot", "Le mot à deviner", false)
                                .setGuildOnly(true)
                                .queue();
                jda.upsertCommand("deviner", "Devinez un mot")
                                .addOption(OptionType.STRING, "mot", "Le mot à deviner", true)
                                .setGuildOnly(true)
                                .queue();
                jda.upsertCommand("reset", "Réinitialisez Thierry").setGuildOnly(true).queue();
                jda.upsertCommand("stop", "Arrêtez la partie en cours").setGuildOnly(true).queue();

        }

}
