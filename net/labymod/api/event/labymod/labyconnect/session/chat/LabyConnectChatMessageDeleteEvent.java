// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.chat;

import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;

public class LabyConnectChatMessageDeleteEvent extends LabyConnectChatEvent
{
    private final ChatMessage message;
    
    public LabyConnectChatMessageDeleteEvent(final LabyConnect api, final Chat chat, final ChatMessage message) {
        super(api, chat);
        this.message = message;
    }
    
    public ChatMessage message() {
        return this.message;
    }
}
