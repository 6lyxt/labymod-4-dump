// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface InternalChatModifier
{
    void fireChatClear(final boolean p0);
    
    void deleteMessage(final ChatMessage p0);
    
    void editMessage(final ChatMessage p0, final Component p1);
}
