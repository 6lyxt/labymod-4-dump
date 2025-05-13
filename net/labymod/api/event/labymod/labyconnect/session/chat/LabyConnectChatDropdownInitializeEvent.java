// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.event.Event;

public class LabyConnectChatDropdownInitializeEvent implements Event
{
    private final Friend friend;
    private final ContextMenu menu;
    
    public LabyConnectChatDropdownInitializeEvent(@NotNull final Friend friend, @NotNull final ContextMenu menu) {
        this.friend = friend;
        this.menu = menu;
    }
    
    @NotNull
    public Friend friend() {
        return this.friend;
    }
    
    @NotNull
    public ContextMenu menu() {
        return this.menu;
    }
}
