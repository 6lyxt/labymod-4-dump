// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.advanced;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatDuplicateMessageHandler
{
    private static final String LAST_MESSAGE_KEY = "ChatDuplicateMessages-LastMessage";
    private static final String IGNORE_CHANGES_KEY = "ChatDuplicateMessages-IgnoreChanges";
    
    @Inject
    public ChatDuplicateMessageHandler() {
    }
    
    @Subscribe(126)
    public void handleDuplicateChatMessage(final AdvancedChatTabMessageEvent event) {
        final ChatTab tab = event.tab();
        if (!tab.config().combineChatMessages().get()) {
            return;
        }
        final AdvancedChatMessage message = event.message();
        final DuplicateMessage lastMessage = tab.metadata().get("ChatDuplicateMessages-LastMessage");
        if (lastMessage != null) {
            final ChatMessage chatMessage = message.chatMessage();
            final ChatMessage lastChatMessage = lastMessage.message.chatMessage();
            final boolean ignoreChanges = chatMessage.metadata().getBoolean("ChatDuplicateMessages-IgnoreChanges", false);
            final boolean lastIgnoreChanges = lastChatMessage.metadata().getBoolean("ChatDuplicateMessages-IgnoreChanges", false);
            final Component component = ignoreChanges ? chatMessage.originalComponent() : chatMessage.component();
            final Component lastComponent = lastIgnoreChanges ? lastChatMessage.originalComponent() : lastChatMessage.component();
            if (component.equals(lastComponent)) {
                final DuplicateMessage duplicateMessage = lastMessage;
                ++duplicateMessage.sentCount;
                lastMessage.message.hideMessage();
                lastMessage.message = message;
                event.setMessage(((BaseComponent<Component>)Component.empty().append(message.component())).append(((BaseComponent<Component>)Component.space()).append(Component.text("(" + lastMessage.sentCount, NamedTextColor.GRAY))));
                return;
            }
        }
        tab.metadata().set("ChatDuplicateMessages-LastMessage", new DuplicateMessage(message));
    }
    
    private static class DuplicateMessage
    {
        private AdvancedChatMessage message;
        private int sentCount;
        
        private DuplicateMessage(final AdvancedChatMessage message) {
            this.sentCount = 1;
            this.message = message;
        }
    }
}
