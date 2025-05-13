// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.event.Event;

public class LabyConnectChatInitializeEvent implements Event
{
    private final Friend friend;
    private final HorizontalListWidget buttonContainer;
    
    public LabyConnectChatInitializeEvent(@NotNull final Friend friend, @NotNull final HorizontalListWidget buttonContainer) {
        this.friend = friend;
        this.buttonContainer = buttonContainer;
    }
    
    @NotNull
    public Friend friend() {
        return this.friend;
    }
    
    @NotNull
    public HorizontalListWidget buttonContainer() {
        return this.buttonContainer;
    }
}
