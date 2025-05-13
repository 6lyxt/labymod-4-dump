// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<eja> getMessages();
    
    List<eja.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final ss p1, final eja p2);
    
    void setMessageMeta(final ss p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final ss p0);
}
