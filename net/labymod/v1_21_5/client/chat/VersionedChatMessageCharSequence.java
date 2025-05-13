// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements azk
{
    private final ChatMessage message;
    private final azk wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final azk wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final azl sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
