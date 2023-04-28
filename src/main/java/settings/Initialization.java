package settings;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Initialization {
    public static void main(String[] args) {
        JDA bot = JDABuilder.createDefault("MTEwMTU4MTU3ODAwNzc2MDkzNg.GRa2zD._anOnNiXr6Gv-wGUZspZOEEy273LGuVyMAX9uo")
                .setActivity(Activity.playing("MOTUS"))
                .build();

    }
}
