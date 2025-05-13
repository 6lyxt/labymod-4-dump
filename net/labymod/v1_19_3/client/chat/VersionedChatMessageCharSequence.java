// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements ano
{
    private final ChatMessage message;
    private final ano wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final ano wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final anp sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
