// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements ags
{
    private final ChatMessage message;
    private final ags wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final ags wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final agt sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
