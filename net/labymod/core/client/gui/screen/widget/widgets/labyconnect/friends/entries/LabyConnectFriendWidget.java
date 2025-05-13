// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.LabyConnectDirectChatActivity;
import net.labymod.api.LabyAPI;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatDropdownInitializeEvent;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.TextComponent;
import java.util.Locale;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import java.util.List;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.user.group.Group;
import net.labymod.api.user.GameUser;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.util.StringUtil;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget;

@AutoWidget
public class LabyConnectFriendWidget extends LabyConnectEntryWidget
{
    private final Friend friend;
    
    public LabyConnectFriendWidget(final ParentScreen contentDisplay, final Friend friend) {
        super(contentDisplay);
        this.friend = friend;
        this.setContextMenu(createContextMenu(friend));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean online = this.friend.isOnline();
        if (online) {
            this.removeId("offline");
            this.addId("online");
        }
        else {
            this.removeId("online");
            this.addId("offline");
        }
        final GameUser user = Laby.references().gameUserService().gameUser(this.friend.getUniqueId());
        final Group group = user.visibleGroup();
        final IconWidget widgetHead = new IconWidget(Icon.head(this.friend.getUniqueId()));
        widgetHead.addId("icon");
        ((AbstractWidget<IconWidget>)this).addChild(widgetHead);
        ComponentWidget widgetName = ComponentWidget.component(Component.text(this.friend.getName(), this.friend.isOnline() ? TextColor.color(group.getColor().getRGB()) : NamedTextColor.GRAY));
        widgetName.addId("text");
        final UserStatus userStatus = this.friend.userStatus();
        if (userStatus != UserStatus.OFFLINE) {
            Component statusComponent = null;
            final Chat chat = this.friend.chat();
            final List<ChatMessage> messages = chat.getMessages();
            if (!messages.isEmpty()) {
                final ChatMessage message = messages.get(messages.size() - 1);
                if (message instanceof final TextChatMessage textChatMessage) {
                    statusComponent = textChatMessage.toComponent();
                }
            }
            else {
                final ServerInfo server = this.friend.getServer();
                if (server != null) {
                    statusComponent = ((BaseComponent<Component>)Component.text(server.getDisplayInfo())).color(NamedTextColor.GRAY);
                }
            }
            if (statusComponent != null) {
                final VerticalListWidget<Widget> textContainer = (VerticalListWidget<Widget>)new VerticalListWidget().addId("text-container");
                textContainer.addChild(widgetName);
                widgetName = null;
                final ComponentWidget widget = ComponentWidget.component(statusComponent);
                widget.addId("status");
                textContainer.addChild(widget);
                ((AbstractWidget<VerticalListWidget<Widget>>)this).addChild(textContainer);
            }
            final IconWidget indicatorWidget = new IconWidget(Textures.SpriteCommon.STATUS_INDICATOR);
            final String statusId = StringUtil.toLowercase(userStatus.name());
            indicatorWidget.addId("indicator", "status-" + statusId);
            indicatorWidget.color().set(userStatus.getColor().getRGB());
            ((AbstractWidget<IconWidget>)this).addChild(indicatorWidget);
        }
        if (widgetName != null) {
            ((AbstractWidget<ComponentWidget>)this).addChild(widgetName);
        }
        if (this.friend.chat().getUnreadCount() > 0) {
            final DivWidget unreadContainer = new DivWidget();
            unreadContainer.addId("unread-container");
            final ComponentWidget widgetUnread = ComponentWidget.component(Component.text(this.friend.chat().getUnreadCount()));
            widgetUnread.addId("unread");
            ((AbstractWidget<ComponentWidget>)unreadContainer).addChild(widgetUnread);
            ((AbstractWidget<DivWidget>)this).addChild(unreadContainer);
        }
        if (this.friend.isPinned()) {
            final IconWidget widgetPin = new IconWidget(Textures.SpriteCommon.PIN);
            widgetPin.addId("pin");
            ((AbstractWidget<IconWidget>)this).addChild(widgetPin);
        }
    }
    
    public Friend friend() {
        return this.friend;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof LabyConnectFriendWidget && ((LabyConnectFriendWidget)obj).friend().getUniqueId().equals(this.friend().getUniqueId());
    }
    
    @Override
    public int hashCode() {
        return this.friend().getUniqueId().hashCode();
    }
    
    @Override
    public int getSortingValue() {
        return 1;
    }
    
    public static ContextMenu createContextMenu(final Friend friend) {
        final ContextMenu contextMenu = new ContextMenu();
        final LabyAPI labyAPI = Laby.labyAPI();
        final String actionPrefix = "labymod.activity.labyconnect.chat.action.%s";
        if (friend.getServer() != null) {
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.multiplayer.joinServer", new Component[0])).clickHandler((entry, sourceWidget) -> {
                final ServerInfo server = friend.getServer();
                if (server == null) {
                    return;
                }
                else {
                    final ServerAddress address = ServerAddress.parse(server.getAddress() + ":" + server.getPort());
                    labyAPI.serverController().joinServer(ConnectableServerData.builder().name(address.toString()).address(address).build());
                    return;
                }
            }).build());
        }
        contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable(String.format(Locale.ROOT, actionPrefix, "labynet"), new Component[0]).arguments(((BaseComponent<Component>)Component.text("laby.net").decorate(TextDecoration.BOLD)).color(NamedTextColor.YELLOW))).clickHandler((entry, sourceWidget) -> labyAPI.minecraft().chatExecutor().openUrl(String.format(Locale.ROOT, "https://laby.net/@%s", friend.getName()))).build());
        contextMenu.addEntry(ContextMenuEntry.builder().text(() -> Component.translatable(String.format(Locale.ROOT, actionPrefix, friend.isPinned() ? "unpin" : "pin"), new Component[0])).clickHandler((entry, sourceWidget) -> {
            if (friend.isPinned()) {
                friend.unpin();
            }
            else {
                friend.pin();
            }
            return;
        }).build());
        contextMenu.addEntry(ContextMenuEntry.builder().text(() -> Component.translatable(String.format(Locale.ROOT, actionPrefix, "note"), new Component[0])).clickHandler((entry, sourceWidget) -> friend.openNoteEditor()).build());
        contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable(String.format(Locale.ROOT, actionPrefix, "remove"), new Component[0])).clickHandler((entry, sourceWidget) -> {
            PopupWidget.builder().title(Component.translatable("labymod.activity.labyconnect.chat.action.remove", new Component[0])).text(Component.translatable("labymod.activity.labyconnect.chat.action.dialogue.subText", new Component[0]));
            Objects.requireNonNull(friend);
            final Object o;
            final PopupWidget popupWidget = ((PopupWidget.Builder)o).confirmCallback(friend::remove).build();
            popupWidget.displayInOverlay();
            return;
        }).build());
        Laby.fireEvent(new LabyConnectChatDropdownInitializeEvent(friend, contextMenu));
        return contextMenu;
    }
    
    @Override
    public void select() {
        final LabyScreen currentLabyScreen = this.contentDisplay.currentLabyScreen();
        if (currentLabyScreen instanceof final LabyConnectDirectChatActivity activity) {
            final Friend previousFriend = activity.friend();
            if (previousFriend.equals(this.friend)) {
                return;
            }
        }
        this.displayContentActivity(new LabyConnectDirectChatActivity(this.friend));
    }
}
