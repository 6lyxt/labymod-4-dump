// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.chat;

import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;

@Deprecated
public class ChatTabMessageEvent extends AdvancedChatTabMessageEvent
{
    public ChatTabMessageEvent(@NotNull final ChatTab tab, @NotNull final AdvancedChatMessage originalMessage) {
        super(tab, originalMessage);
    }
}
