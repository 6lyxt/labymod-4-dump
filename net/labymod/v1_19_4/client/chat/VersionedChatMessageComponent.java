// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

public class VersionedChatMessageComponent extends tw
{
    private final ChatMessage message;
    
    public VersionedChatMessageComponent(final ChatMessage message, final tj wrapped) {
        super(tk.a, (List)List.of(wrapped), uf.a);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
