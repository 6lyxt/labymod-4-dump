// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.chat;

import java.util.List;

public interface VersionedGuiNewChat
{
    List<bhx> getMessages();
    
    List<bhx> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final hh p1, final bhx p2);
    
    void setChatVisibility(final hh p0, final aed.b p1);
    
    void clearChatVisibility(final hh p0);
}
