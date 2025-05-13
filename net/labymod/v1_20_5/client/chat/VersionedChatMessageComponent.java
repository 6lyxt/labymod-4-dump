// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends yd
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final xp wrapped) {
        super((xq)yw.c, (List)List.of(wrapped), ym.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
