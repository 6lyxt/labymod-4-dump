// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements arj
{
    private final ChatMessage message;
    private final arj wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final arj wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final ark sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
