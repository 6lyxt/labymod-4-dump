// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<emb> getMessages();
    
    List<emb.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final tj p1, final emb p2);
    
    void setMessageMeta(final tj p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final tj p0);
}
