// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ChatReceiveEvent extends DefaultCancellable implements Event
{
    private final ChatMessage chatMessage;
    private boolean modified;
    
    public ChatReceiveEvent(@NotNull final ChatMessage message) {
        this.chatMessage = message;
    }
    
    @NotNull
    public ChatMessage chatMessage() {
        return this.chatMessage;
    }
    
    @NotNull
    public Component message() {
        return this.chatMessage.component();
    }
    
    public void setMessage(@NotNull final Component message) {
        Objects.requireNonNull(message, "Message cannot be null, cancel the event instead!");
        this.chatMessage.edit(message);
        this.modified = true;
    }
    
    public boolean isModified() {
        return this.modified;
    }
}
