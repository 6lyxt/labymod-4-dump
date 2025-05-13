// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model.chat;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.labyconnect.protocol.model.User;
import java.util.List;
import java.util.UUID;

public interface Chat
{
    UUID getId();
    
    List<ChatMessage> getMessages();
    
    List<User> getParticipants();
    
    boolean isTyping();
    
    void sendMessage(final String p0);
    
    void sendFile(final String p0, final byte[] p1);
    
    void sendImage(final GameImage p0) throws Exception;
    
    default void addMessage(final User sender, final String message) {
        this.addMessage(sender, message, TimeUtil.getCurrentTimeMillis());
    }
    
    default void addMessage(final User sender, final String message, final long timestamp) {
        this.addMessage(sender, message, timestamp, null);
    }
    
    void addMessage(final User p0, final String p1, final long p2, final Metadata p3);
    
    void addParticipant(final User p0);
    
    void removeParticipant(final User p0);
    
    Component title();
    
    Icon icon();
    
    int getUnreadCount();
    
    String getInputMessage();
    
    void setInputMessage(final String p0);
    
    @Nullable
    ChatMessage getLastMessage();
    
    void openChat();
    
    @Deprecated
    default void addIncomingMessage(final User sender, final String message, final long timestamp, final Metadata metadata) {
        this.addMessage(sender, message, timestamp, metadata);
    }
}
