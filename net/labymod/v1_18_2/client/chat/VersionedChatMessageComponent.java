// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.chat;

import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends qx
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final qk wrapped) {
        super("");
        this.message = message;
        super.a(wrapped);
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
