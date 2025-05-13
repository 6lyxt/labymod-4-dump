// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.advanced;

import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;
import java.util.Objects;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import java.util.Collection;
import java.util.Optional;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.util.AddressUtil;
import net.labymod.api.util.I18n;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import java.util.List;
import net.labymod.api.configuration.labymod.chat.ChatTab;

public final class IngameChatTab extends ChatTab
{
    public static final String CUSTOM_BACKGROUND = "custom_background";
    private final List<AdvancedChatMessage> messages;
    private int counter;
    
    public IngameChatTab(@NotNull final ChatWindow chatWindow, @NotNull final RootChatTabConfig rootConfig) {
        super(chatWindow, rootConfig);
        this.messages = new ArrayList<AdvancedChatMessage>();
    }
    
    @NotNull
    @Override
    public String getCustomName() {
        final ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData == null) {
            return I18n.translate("labymod.ui.navigation.singleplayer", new Object[0]);
        }
        String host = serverData.address().toString();
        if (AddressUtil.isLocalHost(host)) {
            host = "localhost";
        }
        final Optional<ServerGroup> serverGroup = Laby.labyAPI().labyNetController().getServerByIp(host);
        if (serverGroup.isPresent()) {
            return serverGroup.get().getNiceName();
        }
        return serverData.getName();
    }
    
    @Override
    public void copy(@NotNull final ChatTab chatTab) {
        if (chatTab instanceof final IngameChatTab ingameChatTab) {
            this.messages.addAll(ingameChatTab.messages);
        }
    }
    
    @NotNull
    @Override
    public Widget createContentWidget(final WindowAccessor window) {
        return new ChatMessagesWidget(this, window);
    }
    
    @Override
    public void invalidateCache() {
        super.invalidateCache();
        for (final AdvancedChatMessage message : this.messages) {
            message.invalidateCache();
        }
    }
    
    @NotNull
    public List<AdvancedChatMessage> getMessages() {
        return this.messages;
    }
    
    public void handleInput(@NotNull final AdvancedChatMessage message) {
        Objects.requireNonNull(message, "Message cannot be null!");
        final AdvancedChatTabMessageEvent event = new AdvancedChatTabMessageEvent(this, message);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        for (int limit = this.config().chatLimit().get(), i = this.messages.size(); i >= limit; --i) {
            this.messages.remove(i - 1);
        }
        this.messages.add(0, message);
        ++this.counter;
        ++this.unread;
    }
    
    public void handleMessageDelete(@NotNull final ChatMessage message) {
        this.messages.removeIf(advancedMessage -> advancedMessage.chatMessage().messageId().equals(message.messageId()));
    }
    
    public void handleMessageUpdate(@NotNull final ChatMessage message) {
        for (final AdvancedChatMessage advancedMessage : this.messages) {
            if (advancedMessage.chatMessage().messageId().equals(message.messageId())) {
                advancedMessage.invalidateCache();
                break;
            }
        }
    }
    
    public int getCounter() {
        return this.counter;
    }
}
