// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<fld> getMessages();
    
    List<fld.a> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final wp p1, final fld p2);
    
    void setMessageMeta(final wp p0, final ChatMessageMeta p1);
    
    void clearMessageMeta(final wp p0);
}
