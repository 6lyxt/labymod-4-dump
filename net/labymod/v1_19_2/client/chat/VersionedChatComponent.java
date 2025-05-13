// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<efp> getMessages();
    
    List<efp.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final rq p1, final efp p2);
    
    void setMessageMeta(final rq p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final rq p0);
}
