// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.advanced;

import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatWidget;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.configuration.labymod.chat.ChatTab;

public class LabyChatTab extends ChatTab
{
    private final ListSession<Widget> listSession;
    private final Friend friend;
    
    protected LabyChatTab(@NotNull final ChatWindow chatWindow, @NotNull final RootChatTabConfig rootConfig, final Friend friend) {
        super(chatWindow, rootConfig);
        this.friend = friend;
        this.listSession = new ListSession<Widget>();
    }
    
    @Override
    public void copy(@NotNull final ChatTab chatTab) {
    }
    
    @NotNull
    @Override
    public String getCustomName() {
        return this.friend.getName();
    }
    
    @NotNull
    @Override
    public Widget createContentWidget(final WindowAccessor window) {
        return new LabyConnectChatWidget(this.friend.chat(), this.listSession);
    }
}
