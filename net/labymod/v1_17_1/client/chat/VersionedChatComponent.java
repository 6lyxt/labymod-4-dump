// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<dvk<os>> getMessages();
    
    List<dvk<ags>> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final os p1, final dvk<os> p2);
}
