// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat.advanced;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

public class AdvancedChatReceiveEvent extends ChatReceiveEvent
{
    public AdvancedChatReceiveEvent(@NotNull final ChatMessage message) {
        super(message);
    }
}
