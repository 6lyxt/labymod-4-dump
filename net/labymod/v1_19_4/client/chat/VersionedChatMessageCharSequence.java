// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageCharSequence implements aov
{
    private final ChatMessage message;
    private final aov wrapped;
    
    public VersionedChatMessageCharSequence(final ChatMessage message, final aov wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }
    
    public boolean accept(final aow sink) {
        return this.wrapped.accept(sink);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
