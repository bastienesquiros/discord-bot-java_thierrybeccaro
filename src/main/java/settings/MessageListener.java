package settings;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();

        if (message.toLowerCase().contains("thibault")) {
            event.getChannel().sendMessage("Vous vouliez dire TIBOLTHE non ? MO MO MOTUS !").queue();
        } else if (message.toLowerCase().contains("peter")) {
            event.getChannel().sendMessage("Vous vouliez dire PETEUR non ? MO MO MOTUS !").queue();
        } else if (message.toLowerCase().contains("jun")) {
            event.getChannel().sendMessage("Vous vouliez dire JEANNE non ? MO MO MOTUS !").queue();
        }
    }

}
