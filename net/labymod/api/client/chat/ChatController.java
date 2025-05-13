// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ChatController
{
    int getMaxHistorySize();
    
    List<ChatMessage> getMessages();
    
    @Deprecated
    ChatMessage addMessage(final Component p0);
    
    ChatMessage addMessage(final ChatMessage.Builder p0);
    
    ChatMessage addMessage(final ChatMessage p0);
    
    void clear();
    
    ChatMessage messageAt(final int p0);
    
    void storeState();
    
    void restoreState();
}
