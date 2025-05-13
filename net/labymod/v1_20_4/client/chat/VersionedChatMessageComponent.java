// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends vt
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final vf wrapped) {
        super((vg)wm.c, (List)List.of(wrapped), wc.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
