// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.activities.ingame.chat;

import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ChatAccessor
{
    ChatProvider getProvider();
    
    boolean isChatOpen();
    
    void reload();
}
