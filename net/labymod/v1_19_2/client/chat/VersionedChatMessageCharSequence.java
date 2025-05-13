// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements alu
{
    private final ChatMessage message;
    private final alu wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final alu wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final alv sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
