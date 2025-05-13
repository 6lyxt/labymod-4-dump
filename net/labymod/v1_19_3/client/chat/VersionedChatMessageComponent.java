// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends tf
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final ss wrapped) {
        super(st.a, (List)List.of(wrapped), to.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
