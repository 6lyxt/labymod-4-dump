// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet;

import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.component.Component;
import net.labymod.api.labynet.models.ServerChat;
import java.util.regex.Matcher;
import net.labymod.api.labynet.event.ServerDataEvent;
import net.labymod.api.event.client.chat.advanced.AdvancedChatReceiveEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.client.render.font.ComponentRenderer;
import javax.inject.Singleton;

@Singleton
public class ServerChatDataHandler
{
    private final ComponentRenderer componentRenderer;
    private final LabyNetController labyNetController;
    
    @Inject
    public ServerChatDataHandler(final LabyNetController labyNetController) {
        this.componentRenderer = Laby.references().componentRenderer();
        this.labyNetController = labyNetController;
    }
    
    @Subscribe
    public void handleChatMessage(final ChatReceiveEvent event) {
        if (event instanceof AdvancedChatReceiveEvent) {
            return;
        }
        this.labyNetController.getCurrentServer().ifPresent(serverGroup -> {
            final ServerChat chat = serverGroup.getChat();
            if (chat != null && !chat.getEventMessages().isEmpty()) {
                final Component message = event.chatMessage().component();
                final String rawMessage = this.componentRenderer.plainSerializer().serialize(message);
                chat.findEventMessage(rawMessage).ifPresent(eventMessage -> {
                    final Matcher matcher = eventMessage.pattern().matcher(rawMessage);
                    if (!(!matcher.find())) {
                        final String[] arguments = new String[matcher.groupCount()];
                        for (int i = 0; i < arguments.length; ++i) {
                            arguments[i] = matcher.group(i + 1);
                        }
                        Laby.fireEvent(new ServerDataEvent(eventMessage.type(), arguments));
                    }
                });
            }
        });
    }
}
