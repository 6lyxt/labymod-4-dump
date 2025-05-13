// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends sb
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final rq wrapped) {
        super(rr.a, (List)List.of(wrapped), sj.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
