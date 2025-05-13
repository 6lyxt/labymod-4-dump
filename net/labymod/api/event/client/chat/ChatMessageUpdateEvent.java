// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ChatMessageUpdateEvent extends DefaultCancellable implements Event
{
    private final Phase phase;
    private final ChatMessage message;
    private final Component prevComponent;
    private final Component newComponent;
    
    public ChatMessageUpdateEvent(@NotNull final Phase phase, @NotNull final ChatMessage message, @NotNull final Component prevComponent, @Nullable final Component newComponent) {
        this.phase = phase;
        this.message = message;
        this.prevComponent = prevComponent;
        this.newComponent = newComponent;
    }
    
    @NotNull
    public Phase phase() {
        return this.phase;
    }
    
    @NotNull
    public ChatMessage message() {
        return this.message;
    }
    
    @NotNull
    public Component prevComponent() {
        return this.prevComponent;
    }
    
    @Nullable
    public Component newComponent() {
        return this.newComponent;
    }
}
