// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends fa
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final eu wrapped) {
        super("");
        this.message = message;
        super.a(wrapped);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
