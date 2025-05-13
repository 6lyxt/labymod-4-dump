// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.activities.ingame.chat;

public interface WindowAccessor
{
    ChatAccessor chat();
    
    boolean hasTitleBar();
    
    void createNewTab();
}
