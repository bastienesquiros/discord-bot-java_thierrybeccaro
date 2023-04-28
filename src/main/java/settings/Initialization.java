package settings;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Initialization {
    public static void main(String[] args) {
        JDABuilder.createDefault("MTEwMTU4MTU3ODAwNzc2MDkzNg.GRa2zD._anOnNiXr6Gv-wGUZspZOEEy273LGuVyMAX9uo")
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                        GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("MOTUS"))
                .addEventListeners(new EventsListener())
                .build();

    }
}
