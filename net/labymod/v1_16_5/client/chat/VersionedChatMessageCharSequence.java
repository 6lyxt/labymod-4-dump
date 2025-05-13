// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements afa
{
    private final ChatMessage message;
    private final afa wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final afa wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final afb sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
