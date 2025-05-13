// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends xd
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final wp wrapped) {
        super((wq)xw.c, (List)List.of(wrapped), xm.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
