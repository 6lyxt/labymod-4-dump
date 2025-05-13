// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.chat;

import java.util.List;

public interface VersionedChatComponent
{
    List<dym<qk>> getMessages();
    
    List<dym<aiz>> getFormattedMessages();
    
    void injectFormattedMessages(final int p0, final qk p1, final dym<qk> p2);
}
