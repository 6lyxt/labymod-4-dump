// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<dju<nr>> getMessages();
    
    List<dju<afa>> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final nr p1, final dju<nr> p2);
}
