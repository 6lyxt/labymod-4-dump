// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.chat;

import java.util.List;

public interface VersionedGuiNewChat
{
    List<ava> getMessages();
    
    List<ava> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final eu p1, final ava p2);
    
    void setChatVisibility(final eu p0, final wn.b p1);
    
    void clearChatVisibility(final eu p0);
}
