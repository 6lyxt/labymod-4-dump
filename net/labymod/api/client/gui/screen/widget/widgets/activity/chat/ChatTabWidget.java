// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.chat;

import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public abstract class ChatTabWidget extends AbstractWidget<Widget>
{
    private final RootChatTabConfig tab;
    protected boolean chatOpen;
    private int unreadMessages;
    private Runnable messageHandler;
    
    protected ChatTabWidget(final RootChatTabConfig tab) {
        this.unreadMessages = 0;
        this.tab = tab;
    }
    
    public RootChatTabConfig getChatTab() {
        return this.tab;
    }
    
    public int getUnreadMessages() {
        return this.unreadMessages;
    }
    
    public Component getUnreadMessagesComponent() {
        return (this.unreadMessages > 9) ? Component.text("!!!") : Component.text(this.unreadMessages);
    }
    
    public void setMessageHandler(final Runnable messageHandler) {
        this.messageHandler = messageHandler;
    }
    
    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (visible) {
            this.unreadMessages = 0;
            if (this.messageHandler != null) {
                this.messageHandler.run();
            }
        }
    }
    
    public void onChatClose() {
        this.setActive(this.chatOpen = false);
    }
    
    public void onChatOpen() {
        this.setActive(this.chatOpen = true);
    }
    
    public abstract void handleInput(final String p0);
}
