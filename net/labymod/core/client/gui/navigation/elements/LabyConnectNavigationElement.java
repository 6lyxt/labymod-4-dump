// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageReadEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;

public class LabyConnectNavigationElement extends ScreenNavigationElement
{
    public static final LabyConnectActivity ACTIVITY;
    
    public LabyConnectNavigationElement() {
        super(LabyConnectNavigationElement.ACTIVITY);
    }
    
    @Override
    public String getWidgetId() {
        return "labyconnect";
    }
    
    @Override
    public Component getDisplayName() {
        final Component component = Component.translatable("labymod.ui.navigation.labyconnect", new Component[0]);
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return component;
        }
        final int unreadCount = session.getUnreadCount();
        if (unreadCount == 0) {
            return component;
        }
        component.append(Component.text(" "));
        component.append(Component.text("(", NamedTextColor.GRAY));
        component.append(Component.text(unreadCount, NamedTextColor.RED));
        component.append(Component.text(")", NamedTextColor.GRAY));
        return component;
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.CHAT_BUBBLE;
    }
    
    @Subscribe
    public void onLabyConnectChatMessage(final LabyConnectChatMessageEvent event) {
        this.updateWidget();
    }
    
    @Subscribe
    public void onLabyConnectChatMessageRead(final LabyConnectChatMessageReadEvent event) {
        this.updateWidget();
    }
    
    static {
        ACTIVITY = new LabyConnectActivity();
    }
}
