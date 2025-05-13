// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<fgi> getMessages();
    
    List<fgi.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final wz p1, final fgi p2);
    
    void setMessageMeta(final wz p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final wz p0);
}
