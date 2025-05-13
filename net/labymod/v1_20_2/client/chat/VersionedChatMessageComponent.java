// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends ty
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final tl wrapped) {
        super(tm.a, (List)List.of(wrapped), uh.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
