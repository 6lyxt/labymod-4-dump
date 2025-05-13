// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.Textures;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import java.util.Optional;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.api.util.Debounce;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.time.TimeUtil;
import java.util.ArrayList;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.api.Constants;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import java.util.HashSet;
import java.util.HashMap;
import net.labymod.api.Laby;
import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import net.labymod.core.labynet.DefaultLabyNetController;
import net.labymod.core.main.notification.DefaultNotificationController;
import net.labymod.api.labyconnect.LabyConnect;

public class LabyConnectNotifications
{
    private final LabyConnect labyConnect;
    private final DefaultNotificationController notifications;
    private final DefaultLabyNetController labyNetController;
    private final Map<UUID, List<String>> previousGameModes;
    private final Set<UUID> announcedFriendRequests;
    
    public LabyConnectNotifications(final LabyConnect labyConnect) {
        this.labyConnect = labyConnect;
        this.notifications = (DefaultNotificationController)Laby.labyAPI().notificationController();
        this.labyNetController = (DefaultLabyNetController)Laby.references().labyNetController();
        this.previousGameModes = new HashMap<UUID, List<String>>();
        this.announcedFriendRequests = new HashSet<UUID>();
    }
    
    @Subscribe
    public void onLabyConnectDisconnect(final LabyConnectDisconnectEvent event) {
        this.previousGameModes.clear();
        if (event.getInitiator() == LabyConnectDisconnectEvent.Initiator.USER) {
            return;
        }
        this.pushSystemMessage(Component.translatable("labymod.activity.labyconnect.notifications.disconnect", new Component[0]).args(Component.text(event.getReason())));
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestAddBulk(final LabyConnectIncomingFriendRequestAddBulkEvent event) {
        if (!this.labyConnect.configProvider().get().incomingRequestNotifications().get()) {
            return;
        }
        this.pushSystemMessage(Component.translatable("labymod.activity.labyconnect.notifications.requests", new Component[0]).args(Component.text(event.getRequests().size())));
    }
    
    @Subscribe
    public void onLabyConnectIncomingFriendRequestAdd(final LabyConnectIncomingFriendRequestAddEvent event) {
        if (!this.labyConnect.configProvider().get().incomingRequestNotifications().get()) {
            return;
        }
        if (!this.announcedFriendRequests.add(event.request().getUniqueId())) {
            return;
        }
        this.pushRequestMessage(event.request(), Component.translatable("labymod.activity.labyconnect.notifications.request", new Component[0]));
    }
    
    @Subscribe
    public void onLabyConnectChatMessage(final LabyConnectChatMessageEvent event) {
        final ChatMessage message2 = event.message();
        if (!(message2 instanceof TextChatMessage)) {
            return;
        }
        final TextChatMessage message = (TextChatMessage)message2;
        if (message.sender().isSelf()) {
            return;
        }
        if (!this.labyConnect.configProvider().get().incomingChatMessageNotifications().get()) {
            return;
        }
        Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_CHAT_MESSAGE, 1.0f, 1.5f);
        Component text = message.toComponent();
        if (text == null) {
            text = Component.empty();
        }
        this.pushChatMessage(event.chat(), text);
    }
    
    @Subscribe
    public void onLabyConnectFriendStatus(final LabyConnectFriendStatusEvent event) {
        final boolean online = event.isOnline();
        if (event.wasOnline() == online) {
            return;
        }
        this.previousGameModes.remove(event.friend().getUniqueId());
        if (!this.labyConnect.configProvider().get().onlineStatusNotification().get()) {
            return;
        }
        this.pushUserMessage(event.friend(), ((BaseComponent<Component>)Component.translatable(online ? "labymod.activity.labyconnect.notifications.online" : "labymod.activity.labyconnect.notifications.offline", new Component[0])).color(online ? NamedTextColor.GREEN : NamedTextColor.RED));
    }
    
    @Subscribe
    public void onLabyConnectFriendServer(final LabyConnectFriendServerEvent event) {
        final ServerInfo serverInfo = event.serverInfo();
        if (serverInfo == null) {
            this.previousGameModes.remove(event.friend().getUniqueId());
            return;
        }
        final boolean sameServer = serverInfo.isSameServer(event.getPreviousServer());
        final LabyConnectConfigAccessor labyConnectConfig = this.labyConnect.configProvider().get();
        if (!labyConnectConfig.serverSwitchNotifications().get()) {
            return;
        }
        final String gameModeName = serverInfo.getGameModeName();
        if (sameServer) {
            if (!labyConnectConfig.serverSwitchGameModeNotifications().get()) {
                return;
            }
            final List<String> lastGameModes = this.previousGameModes.computeIfAbsent(event.friend().getUniqueId(), uniqueId -> new ArrayList(2));
            final String actualGameModeName = (gameModeName == null) ? "" : gameModeName;
            if (lastGameModes.contains(actualGameModeName)) {
                return;
            }
            if (lastGameModes.size() > 1) {
                lastGameModes.remove(0);
            }
            lastGameModes.add(actualGameModeName);
        }
        else {
            this.previousGameModes.remove(event.friend().getUniqueId());
        }
        Component text;
        if (gameModeName == null) {
            text = Component.translatable("labymod.activity.labyconnect.notifications.server", NamedTextColor.GRAY, Component.text(serverInfo.getDisplayInfo(), NamedTextColor.WHITE));
        }
        else if (TimeUtil.getCurrentTimeMillis() - event.friend().getLastServerChange() < 1000L) {
            text = Component.translatable("labymod.activity.labyconnect.notifications.gameModeAndServer", NamedTextColor.GRAY, Component.text(gameModeName, NamedTextColor.WHITE), Component.text(serverInfo.getDisplayInfo(), NamedTextColor.WHITE));
        }
        else {
            text = Component.translatable("labymod.activity.labyconnect.notifications.gameMode", NamedTextColor.GRAY, Component.text(gameModeName, NamedTextColor.WHITE));
        }
        final Notification.Builder builder = Notification.builder().title(Component.text(event.friend().getName())).icon(Icon.head(event.friend().getUniqueId())).text(text).type(Notification.Type.SYSTEM);
        final Optional<ServerGroup> serverByIp = this.labyNetController.getServerByIp(serverInfo.getAddress());
        if (serverByIp.isPresent()) {
            final ServerGroup serverGroup = serverByIp.get();
            final Optional<ServerGroup.Attachment> icon = serverGroup.getAttachment("icon");
            icon.ifPresent(attachment -> builder.secondaryIcon(attachment.getIcon()));
        }
        Debounce.of("labyconnect-server-switch-" + String.valueOf(event.friend().getUniqueId()), 1000L, () -> this.notifications.push(builder.build()));
    }
    
    private void pushRequestMessage(final IncomingFriendRequest chat, final Component component) {
        this.notifications.push(Notification.builder().title(Component.text(chat.getName())).icon(Icon.head(chat.getUniqueId())).text(component).type(Notification.Type.SOCIAL).build());
    }
    
    private void pushChatMessage(final Chat chat, final Component component) {
        this.notifications.push(Notification.builder().title(chat.title()).icon(chat.icon()).secondaryIcon(Textures.SpriteCommon.CHAT_BUBBLE).text(component).type(Notification.Type.SOCIAL).onClick(notification -> chat.openChat()).build());
    }
    
    private void pushUserMessage(final User user, final Component component) {
        this.notifications.push(Notification.builder().title(Component.text(user.getName())).icon(Icon.head(user.getUniqueId())).secondaryIcon(Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP).text(component).type(Notification.Type.SOCIAL).build());
    }
    
    private void pushSystemMessage(final Component component) {
        this.notifications.push(Notification.builder().title(Component.translatable("labymod.activity.labyconnect.notifications.title", new Component[0])).text(component).icon(Textures.SpriteCommon.CHAT_BUBBLE).type(Notification.Type.SYSTEM).build());
    }
}
