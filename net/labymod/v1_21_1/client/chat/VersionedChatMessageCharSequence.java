// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements aya
{
    private final ChatMessage message;
    private final aya wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final aya wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final ayb sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
