// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Event;

public class ChatMessageAddEvent implements Event
{
    private final ChatMessage chatMessage;
    
    public ChatMessageAddEvent(@NotNull final ChatMessage message) {
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
}
