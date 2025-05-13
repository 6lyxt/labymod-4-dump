// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<evc> getMessages();
    
    List<evc.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final vf p1, final evc p2);
    
    void setMessageMeta(final vf p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final vf p0);
}
