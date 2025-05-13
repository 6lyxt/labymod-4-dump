// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends tj
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final sw wrapped) {
        super(sx.a, (List)List.of(wrapped), ts.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
