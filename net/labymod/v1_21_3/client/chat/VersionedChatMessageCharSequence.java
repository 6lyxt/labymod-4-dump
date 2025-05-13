// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements azq
{
    private final ChatMessage message;
    private final azq wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final azq wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final azr sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
