// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends xu
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final xg wrapped) {
        super((xh)yn.c, (List)List.of(wrapped), yd.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
