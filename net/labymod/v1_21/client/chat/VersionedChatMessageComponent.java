// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends xn
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final wz wrapped) {
        super((xa)yg.c, (List)List.of(wrapped), xw.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
