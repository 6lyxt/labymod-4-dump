// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.accessor.chat;

public interface ChatScreenAccessor
{
    default void insertChatText(final String text, final boolean override) {
        this.insertChatText(text, override, false);
    }
    
    void insertChatText(final String p0, final boolean p1, final boolean p2);
    
    String getChatText();
}
