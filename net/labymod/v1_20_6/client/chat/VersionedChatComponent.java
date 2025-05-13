// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<ffb> getMessages();
    
    List<ffb.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final xp p1, final ffb p2);
    
    void setMessageMeta(final xp p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final xp p0);
}
