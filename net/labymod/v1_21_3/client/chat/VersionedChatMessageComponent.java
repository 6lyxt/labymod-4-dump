// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends yj
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final xv wrapped) {
        super((xw)zc.c, (List)List.of(wrapped), ys.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
