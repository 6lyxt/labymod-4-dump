// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<fqj> getMessages();
    
    List<fqj.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final xg p1, final fqj p2);
    
    void setMessageMeta(final xg p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final xg p0);
}
