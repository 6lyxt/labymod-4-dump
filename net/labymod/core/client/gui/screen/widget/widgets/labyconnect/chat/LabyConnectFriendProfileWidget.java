// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.text.SimpleDateFormat;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.api.user.group.Group;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.user.GameUser;
import java.util.Locale;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.LabyConnectFriendWidget;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import java.text.DateFormat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class LabyConnectFriendProfileWidget extends SimpleWidget
{
    private static final DateFormat DATE_FORMAT;
    private static final DateFormat TIME_FORMAT;
    private final Friend friend;
    
    public LabyConnectFriendProfileWidget(final Friend friend) {
        this.friend = friend;
        this.setContextMenu(LabyConnectFriendWidget.createContextMenu(friend));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final IconWidget widgetHead = new IconWidget(Icon.head(this.friend.getUniqueId()));
        widgetHead.addId("head");
        ((AbstractWidget<IconWidget>)this).addChild(widgetHead);
        final GameUser gameUser = this.labyAPI.gameUserService().gameUser(this.friend.getUniqueId());
        final TextColor color = gameUser.displayColor();
        final TextComponent nameText = Component.text(this.friend.getName(), color);
        final String note = this.friend.getNote();
        if (note != null) {
            nameText.append(((BaseComponent<Component>)Component.text(" (" + ((note.length() > 20) ? note.substring(0, 20) : note))).color(NamedTextColor.GRAY));
        }
        final ComponentWidget widgetName = ComponentWidget.component(nameText);
        final Group visibleGroup = gameUser.visibleGroup();
        if (note != null || !visibleGroup.isDefault()) {
            Component hoverComponent = Component.empty();
            if (!visibleGroup.isDefault()) {
                hoverComponent = hoverComponent.append(Component.text(visibleGroup.getDisplayName(), color));
            }
            if (!visibleGroup.isDefault() && note != null) {
                hoverComponent = hoverComponent.append(Component.text("\n"));
            }
            if (note != null) {
                hoverComponent = hoverComponent.append(Component.text("(" + note, NamedTextColor.GRAY));
            }
            widgetName.setHoverComponent(hoverComponent);
        }
        widgetName.addId("name");
        ((AbstractWidget<ComponentWidget>)this).addChild(widgetName);
        final ServerInfo server = this.friend.getServer();
        final UserStatus status = this.friend.userStatus();
        Component component;
        if (status == UserStatus.OFFLINE) {
            component = Component.translatable(status.getRemoteTranslationKey(false), status.textColor(), Component.text(LabyConnectFriendProfileWidget.DATE_FORMAT.format(this.friend.getLastOnline())), Component.text(LabyConnectFriendProfileWidget.TIME_FORMAT.format(this.friend.getLastOnline())));
        }
        else if (server != null) {
            String key;
            if (status == UserStatus.BUSY) {
                key = "busy_on";
            }
            else if (status == UserStatus.AWAY) {
                key = "away_on";
            }
            else {
                key = "online_on";
            }
            final Component serverComponent = Component.text(server.getDisplayInfo(), NamedTextColor.WHITE);
            if (server.getGameModeName() != null) {
                serverComponent.append(Component.text(" (")).append(Component.text(server.getGameModeName(), NamedTextColor.GRAY)).append(Component.text(")"));
            }
            component = Component.translatable(String.format(Locale.ROOT, "labymod.activity.labyconnect.chat.status.%s", key), status.textColor(), serverComponent);
        }
        else {
            component = status.component();
        }
        final ComponentWidget widgetStatus = ComponentWidget.component(component);
        widgetStatus.addId("status");
        ((AbstractWidget<ComponentWidget>)this).addChild(widgetStatus);
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
        TIME_FORMAT = new SimpleDateFormat("HH:mm");
    }
}
