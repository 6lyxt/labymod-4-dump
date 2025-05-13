// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.chat;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectChatEvent extends LabyConnectEvent
{
    private final Chat chat;
    
    protected LabyConnectChatEvent(final LabyConnect api, final Chat chat) {
        super(api);
        this.chat = chat;
    }
    
    public Chat chat() {
        return this.chat;
    }
}
