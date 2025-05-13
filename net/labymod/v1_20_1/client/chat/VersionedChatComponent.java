// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<enh> getMessages();
    
    List<enh.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final sw p1, final enh p2);
    
    void setMessageMeta(final sw p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final sw p0);
}
