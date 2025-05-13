// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Event;

public class ChatScreenUpdateEvent implements Event
{
    private final Reason reason;
    
    public ChatScreenUpdateEvent(@NotNull final Reason reason) {
        this.reason = reason;
    }
    
    @NotNull
    public Reason reason() {
        return this.reason;
    }
    
    public enum Reason
    {
        REFRESH_SCREEN, 
        COLORS, 
        ITEM_TOOLTIPS, 
        VISIBILITY;
    }
}
