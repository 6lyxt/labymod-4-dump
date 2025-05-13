// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<eqp> getMessages();
    
    List<eqp.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final tl p1, final eqp p2);
    
    void setMessageMeta(final tl p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final tl p0);
}
