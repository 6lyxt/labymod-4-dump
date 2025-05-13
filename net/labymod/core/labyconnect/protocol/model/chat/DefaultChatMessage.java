// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.chat;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageReadEvent;
import net.labymod.api.Laby;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;

public abstract class DefaultChatMessage implements ChatMessage
{
    protected final Chat chat;
    protected final User sender;
    protected final long timestamp;
    private boolean read;
    private Metadata metadata;
    
    protected DefaultChatMessage(final Chat chat, final User sender, final long timestamp) {
        this.read = false;
        this.metadata = Metadata.create();
        this.chat = chat;
        this.sender = sender;
        this.timestamp = timestamp;
    }
    
    @Override
    public long getTimestamp() {
        return this.timestamp;
    }
    
    @Override
    public void markAsRead() {
        if (!this.read) {
            this.read = true;
            Laby.fireEvent(new LabyConnectChatMessageReadEvent(Laby.labyAPI().labyConnect(), this.chat, this));
        }
    }
    
    @Override
    public boolean isRead() {
        return this.read;
    }
    
    @NotNull
    @Override
    public User sender() {
        return this.sender;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DefaultChatMessage that = (DefaultChatMessage)o;
        return Objects.equals(this.timestamp, that.timestamp);
    }
    
    @Override
    public int hashCode() {
        return (int)(this.timestamp ^ this.timestamp >>> 32);
    }
    
    @Override
    public void deleteMessage() {
        Laby.labyAPI().minecraft().executeNextTick(() -> {
            if (this.chat instanceof final DefaultChat defaultChat) {
                defaultChat.removeMessage(this);
            }
        });
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
}
