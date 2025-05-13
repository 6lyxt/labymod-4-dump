// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.filter;

import net.labymod.api.client.chat.ChatMessage;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface FilterChatService
{
    ChatFilter filter(final List<ChatFilter> p0, final ChatMessage p1);
}
