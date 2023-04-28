package settings;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventsListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();

        if (message.contains("thibault")) {
            event.getChannel().sendMessage("Vous vouliez dire TIBOLTHE non ? MO MO MOTUS !").queue();
        } else if (message.contains("peter")) {
            event.getChannel().sendMessage("Vous vouliez dire PETEUR non ? MO MO MOTUS !").queue();
        } else if (message.contains("jun")) {
            event.getChannel().sendMessage("Vous vouliez dire JEANNE ZZZZZZZZZZ ? MO MO MOTUS !").queue();
        }
    }
}
