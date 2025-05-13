// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<flz> getMessages();
    
    List<flz.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final xv p1, final flz p2);
    
    void setMessageMeta(final xv p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final xv p0);
}
