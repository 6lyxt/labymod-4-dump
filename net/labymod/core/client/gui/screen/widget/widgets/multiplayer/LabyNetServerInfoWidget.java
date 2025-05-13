// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.Textures;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SimpleButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import java.util.Iterator;
import java.util.Optional;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ServerInfo;
import java.util.ArrayList;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.labynet.models.GameMode;
import java.util.List;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.util.Lazy;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.network.server.ConnectableServerData;

@AutoWidget
public class LabyNetServerInfoWidget<T extends ConnectableServerData> extends ServerInfoWidget<T>
{
    protected static final Lazy<ServerListConfig> SERVER_LIST_SETTINGS;
    private final LabyNetServerInfoCache<T> cache;
    private final List<GameMode> gameModes;
    protected String serverGroup;
    protected CompletableResourceLocation background;
    protected CompletableResourceLocation icon;
    protected String userStatsUrl;
    private boolean visibleRefresh;
    private boolean quickJoin;
    private boolean friendHeads;
    
    public LabyNetServerInfoWidget(@NotNull final T serverData, @NotNull final LabyNetServerInfoCache<T> cache) {
        super(serverData, cache.serverInfo(), cache.getSortingValue());
        this.quickJoin = true;
        this.friendHeads = true;
        Objects.requireNonNull(cache, "Cache cannot be null");
        this.cache = cache;
        this.gameModes = new ArrayList<GameMode>();
        this.visibleRefresh = (cache.serverInfo().getStatus() == ServerInfo.Status.LOADING);
        this.cache.setCallback(serverInfoCache -> {
            this.visibleRefresh = false;
            this.updateServerInfo(serverInfoCache.serverInfo());
        });
    }
    
    @Override
    protected Component serverName() {
        if (LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().richServerList().get() && LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().niceNames().get()) {
            final ServerGroup serverGroup = this.cache.getServerGroup();
            if (serverGroup != null) {
                return Component.text(serverGroup.getNiceName());
            }
        }
        return super.serverName();
    }
    
    @Override
    protected void startInitialize(final Parent parent) {
        super.startInitialize(parent);
        if (this.background == null) {
            return;
        }
        final IconWidget backgroundWidget = new IconWidget(Icon.texture(this.background.getCompleted()));
        backgroundWidget.addId("server-background");
        this.addChild(backgroundWidget);
    }
    
    @Override
    protected void finishInitialize(final Parent parent, final FlexibleContentWidget content, final boolean display) {
        super.finishInitialize(parent, content, display);
        this.addQuickPlayButtons(content, display);
        if (this.addFriendHeads(content, display)) {
            this.addId("with-friend-heads");
        }
        else {
            this.removeId("with-friend-heads");
        }
    }
    
    @Override
    protected IconWidget serverIconWidget(final boolean loading) {
        if (!loading && this.icon != null) {
            return new IconWidget(Icon.texture(this.icon.getCompleted()));
        }
        return super.serverIconWidget(loading);
    }
    
    @Override
    public int getListIndex() {
        return this.cache.getSortingValue();
    }
    
    @Override
    protected boolean display() {
        if (this.hasQuickJoinButtons()) {
            this.addId("with-quick-join");
        }
        else {
            this.removeId("with-quick-join");
        }
        if (!LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().richServerList().get() || !LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().highQualityTextures().get()) {
            this.icon = null;
            this.background = null;
            return true;
        }
        final ServerGroup serverGroup = this.cache.getServerGroup();
        if (serverGroup == null) {
            this.serverGroup = null;
            this.icon = null;
            this.background = null;
            return true;
        }
        if (this.serverGroup != null && !this.serverGroup.equals(serverGroup.getServerName())) {
            this.serverGroup = serverGroup.getServerName();
            this.icon = null;
            this.background = null;
        }
        if (this.userStatsUrl == null && serverGroup.getUserStatsUrl() != null) {
            this.userStatsUrl = serverGroup.getUserStatsUrl();
        }
        if (this.background == null) {
            final Optional<ServerGroup.Attachment> optionalBackground = serverGroup.getAttachment("background");
            optionalBackground.ifPresent(attachment -> {
                this.background = attachment.completableResourceLocation();
                if (!this.background.hasResult()) {
                    this.background.addCompletableListener(this::onTextureCompletion);
                }
                return;
            });
        }
        if (this.icon == null) {
            final Optional<ServerGroup.Attachment> optionalIcon = serverGroup.getAttachment("icon");
            optionalIcon.ifPresent(attachment -> {
                this.icon = attachment.completableResourceLocation();
                if (!this.icon.hasResult()) {
                    this.icon.addCompletableListener(this::onTextureCompletion);
                }
                return;
            });
        }
        return (this.icon == null && this.background == null) || (this.serverInfo().getStatus() != ServerInfo.Status.LOADING && (this.icon == null || this.icon.hasResult()) && (this.background == null || this.background.hasResult()));
    }
    
    public LabyNetServerInfoCache<T> cache() {
        return this.cache;
    }
    
    private void onTextureCompletion() {
        this.labyAPI.minecraft().executeNextTick(() -> {
            if (!this.isDestroyed()) {
                if ((this.background == null || this.background.hasResult()) && (this.icon == null || this.icon.hasResult())) {
                    this.reInitialize();
                }
            }
        });
    }
    
    private boolean hasQuickJoinButtons() {
        this.gameModes.clear();
        if (!this.quickJoin || !LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().richServerList().get() || !LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().quickJoinButtons().get()) {
            return false;
        }
        final ServerGroup serverGroup = this.cache.getServerGroup();
        if (serverGroup == null) {
            return false;
        }
        final List<GameMode> availableGameModes = serverGroup.getGameModes();
        if (availableGameModes.isEmpty()) {
            return false;
        }
        for (final GameMode gameMode : availableGameModes) {
            if (this.gameModes.size() > 9) {
                break;
            }
            final String command = gameMode.getCommand();
            if (command == null || command.isEmpty()) {
                continue;
            }
            this.gameModes.add(gameMode);
        }
        return !this.gameModes.isEmpty();
    }
    
    public void refresh(final boolean visible) {
        this.visibleRefresh = visible;
        this.cache.update();
    }
    
    @Override
    protected boolean loadingIcon() {
        return this.visibleRefresh;
    }
    
    private void addQuickPlayButtons(final FlexibleContentWidget content, final boolean display) {
        if (this.gameModes.isEmpty()) {
            return;
        }
        final WrappedListWidget<SimpleButtonWidget> quickJoinButtonList = new WrappedListWidget<SimpleButtonWidget>();
        quickJoinButtonList.addId("quick-join-button-list");
        if (display) {
            for (final GameMode gameMode : this.gameModes) {
                final SimpleButtonWidget button = new SimpleButtonWidget(gameMode.getName());
                button.addId("quick-join-button");
                button.setPressable(() -> this.connect(gameMode.getCommand()));
                quickJoinButtonList.addChild(button);
            }
        }
        content.addContent(quickJoinButtonList);
    }
    
    protected boolean addFriendHeads(final FlexibleContentWidget content, final boolean display) {
        if (!this.friendHeads || !LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().richServerList().get() || !LabyNetServerInfoWidget.SERVER_LIST_SETTINGS.get().friendsInServerList().get()) {
            return false;
        }
        final LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null || session.getFriends().isEmpty()) {
            return false;
        }
        final List<Friend> friends = this.getFriends(session.getFriends());
        if (friends.isEmpty()) {
            return false;
        }
        final HorizontalListWidget friendHeadList = new HorizontalListWidget();
        ((AbstractWidget<Widget>)friendHeadList).addId("friend-head-list");
        if (display) {
            for (final Friend friend : friends) {
                if (friendHeadList.getChildren().size() > 7) {
                    friendHeadList.addEntry(new IconWidget(Textures.SpriteCommon.LARGE_DOTS));
                    break;
                }
                final IconWidget friendIcon = new IconWidget(Icon.head(friend.getUniqueId()));
                friendIcon.setHoverComponent(Component.text(friend.getName()));
                friendIcon.addId("friend-head");
                friendHeadList.addEntry(friendIcon);
            }
        }
        content.addContent(friendHeadList);
        return true;
    }
    
    @Override
    public void connect(final String command) {
        final ServerGroup serverGroup = this.cache.getServerGroup();
        if (serverGroup == null) {
            super.connect(command);
            return;
        }
        super.connect(command, serverGroup.getCommandDelay());
    }
    
    public void setFriendHeadsEnabled(final boolean friendHeads) {
        this.friendHeads = friendHeads;
    }
    
    public void setQuickJoinEnabled(final boolean quickJoin) {
        this.quickJoin = quickJoin;
    }
    
    protected List<Friend> getFriends(final List<Friend> onlineFriends) {
        final List<Friend> friends = (List<Friend>)Lists.newArrayList();
        for (final Friend onlineFriend : onlineFriends) {
            if (!onlineFriend.isOnline()) {
                continue;
            }
            final net.labymod.api.labyconnect.protocol.model.friend.ServerInfo server = onlineFriend.getServer();
            if (server == null) {
                continue;
            }
            if (server.isLocalHost()) {
                continue;
            }
            if (!this.serverData().address().matches(server.getAddress(), server.getPort(), true)) {
                if (this.cache.getServerGroup() == null) {
                    continue;
                }
                final Optional<ServerGroup> serverByIp = this.labyAPI.labyNetController().getServerByIp(server.getAddress());
                if (!serverByIp.isPresent() || !serverByIp.get().equals(this.cache.getServerGroup())) {
                    continue;
                }
                friends.add(onlineFriend);
            }
            else {
                friends.add(onlineFriend);
            }
        }
        return friends;
    }
    
    static {
        SERVER_LIST_SETTINGS = Lazy.of(() -> Laby.labyAPI().config().multiplayer().serverList());
    }
}
