// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ChatClearEvent extends DefaultCancellable implements Event
{
    private final boolean clearHistory;
    
    public ChatClearEvent(final boolean clearHistory) {
        this.clearHistory = clearHistory;
    }
    
    public boolean isClearHistory() {
        return this.clearHistory;
    }
}
