// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat;

import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatClearEvent;
import net.labymod.api.event.client.chat.ChatMessageAddEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.client.component.Component;
import javax.inject.Inject;
import net.labymod.core.client.chat.feature.ChatMessageSenderHandler;
import java.util.ArrayList;
import net.labymod.api.Laby;
import net.labymod.api.event.EventBus;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.client.chat.ChatMessage;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatController;

@Singleton
@Implements(ChatController.class)
public class DefaultChatController implements ChatController
{
    private final InternalChatModifier chatModifier;
    private final List<ChatMessage> messages;
    private final LabyConfig config;
    private State state;
    
    @Inject
    public DefaultChatController(final InternalChatModifier chatModifier, final EventBus eventBus) {
        this.config = Laby.labyAPI().config();
        this.chatModifier = chatModifier;
        this.messages = new ArrayList<ChatMessage>();
        eventBus.registerListener(this);
        eventBus.registerListener(new ChatMessageSenderHandler());
    }
    
    @Override
    public int getMaxHistorySize() {
        return 1000;
    }
    
    @Override
    public List<ChatMessage> getMessages() {
        return this.messages;
    }
    
    @Override
    public ChatMessage addMessage(final Component component) {
        return this.addMessage(ChatMessage.builder().component(component).visibility(ChatVisibility.COMMANDS_ONLY).build());
    }
    
    @Override
    public ChatMessage addMessage(final ChatMessage.Builder builder) {
        return this.addMessage(builder.build());
    }
    
    @Override
    public ChatMessage addMessage(final ChatMessage chatMessage) {
        return this.addMessage(chatMessage, true);
    }
    
    private ChatMessage addMessage(final ChatMessage chatMessage, final boolean justReceived) {
        DefaultChatMessage message = (DefaultChatMessage)chatMessage;
        if (justReceived) {
            final ChatReceiveEvent event = Laby.fireEvent(new ChatReceiveEvent(chatMessage));
            if (event.isCancelled()) {
                return null;
            }
            message.setMessageSent(true);
            if (event.isModified()) {
                message = new DefaultChatMessage(message.messageId(), message.timestamp(), message.originalComponent(), event.message(), message.visibility(), message.trustLevel(), message.getCachedSender());
                message.setMessageSent(true);
            }
        }
        this.messages.add(0, message);
        while (this.messages.size() > this.getMaxHistorySize()) {
            final int index = this.messages.size() - 1;
            this.messages.remove(index);
        }
        Laby.fireEvent(new ChatMessageAddEvent(chatMessage));
        return message;
    }
    
    @Override
    public void clear() {
        this.chatModifier.fireChatClear(true);
    }
    
    @Subscribe(Byte.MAX_VALUE)
    public void handleChatClear(final ChatClearEvent event) {
        if (this.isGlobalAntiChatClear()) {
            return;
        }
        if (!event.isCancelled()) {
            this.messages.clear();
        }
    }
    
    @Override
    public ChatMessage messageAt(final int index) {
        return (this.messages.size() > index) ? this.messages.get(index) : null;
    }
    
    @Override
    public void storeState() {
        this.state = new State(List.copyOf((Collection<? extends ChatMessage>)this.messages));
    }
    
    @Override
    public void restoreState() {
        if (this.isGlobalAntiChatClear()) {
            this.state = null;
            return;
        }
        this.messages.clear();
        if (this.state != null) {
            for (final ChatMessage message : this.state.messages.reversed()) {
                this.addMessage(message, false);
            }
            this.state = null;
        }
    }
    
    private boolean isGlobalAntiChatClear() {
        final AdvancedChatConfig advancedChatConfig = this.config.ingame().advancedChat();
        return advancedChatConfig.enabled().get() && advancedChatConfig.globalAntiChatClear().get();
    }
    
    record State(List<ChatMessage> messages) {}
}
