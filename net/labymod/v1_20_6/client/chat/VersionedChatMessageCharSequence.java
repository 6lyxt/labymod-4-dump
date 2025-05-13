// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements ayl
{
    private final ChatMessage message;
    private final ayl wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final ayl wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final aym sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
