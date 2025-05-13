// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.Laby;
import net.labymod.api.adventure.ComponentUtils;
import net.labymod.api.client.gui.icon.ping.PingType;
import net.labymod.api.util.time.TimeUtil;
import java.util.List;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.ArrayList;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.gui.icon.ping.PingIconRegistry;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.ServerData;

@AutoWidget
@Link("activity/multiplayer/server-info.lss")
public class ServerInfoWidget<T extends ServerData> extends ServerEntryWidget
{
    private static final PingIconRegistry PING_ICON_REGISTRY;
    private final T serverData;
    private ServerInfo serverInfo;
    private FlexibleContentWidget serverInfoWrapper;
    private IconWidget serverIconWidget;
    private IconWidget pingWidget;
    private final int listIndex;
    
    public ServerInfoWidget(@NotNull final T serverData, @NotNull final ServerInfo serverInfo, final int listIndex) {
        Objects.requireNonNull(serverData, "Server data cannot be null");
        Objects.requireNonNull(serverInfo, "Server info cannot be null");
        this.serverData = serverData;
        this.listIndex = listIndex;
        this.addId("server-info");
        this.lazy = true;
        this.updateServerInfo(serverInfo);
    }
    
    public ServerInfoWidget(@NotNull final T serverData, @NotNull final ServerInfo serverInfo) {
        this(serverData, serverInfo, 0);
    }
    
    public ServerInfoWidget(@NotNull final T serverData) {
        this(serverData, ServerInfo.loading(serverData.getName(), serverData.address()));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean display = this.display();
        if (display) {
            this.startInitialize(parent);
        }
        (this.serverIconWidget = this.serverIconWidget(!display)).addId("server-icon");
        this.addSelectionWidgets(this.serverIconWidget);
        this.addChild(this.serverIconWidget);
        ((AbstractWidget<Widget>)(this.serverInfoWrapper = new FlexibleContentWidget())).addId("server-info-wrapper");
        final FlexibleContentWidget serverInfoHeader = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)serverInfoHeader).addId("server-info-header");
        final ComponentWidget serverNameWidget = ComponentWidget.component(this.serverName());
        serverNameWidget.addId("server-name");
        serverInfoHeader.addContent(serverNameWidget);
        final DivWidget serverStatusWrapper = new DivWidget();
        serverStatusWrapper.addId("server-status-wrapper");
        final ComponentWidget serverStatusWidget = this.statusWidget();
        serverStatusWidget.addId("server-status");
        ((AbstractWidget<ComponentWidget>)serverStatusWrapper).addChild(serverStatusWidget);
        serverInfoHeader.addFlexibleContent(serverStatusWrapper);
        (this.pingWidget = this.pingWidget()).addId("server-ping");
        serverInfoHeader.addContent(this.pingWidget);
        this.serverInfoWrapper.addContent(serverInfoHeader);
        final Component description = this.description(display ? null : ServerInfo.Status.LOADING);
        final ComponentWidget descriptionWidget = ComponentWidget.component(description);
        descriptionWidget.addId("server-description");
        this.serverInfoWrapper.addContent(descriptionWidget);
        this.finishInitialize(parent, this.serverInfoWrapper, display);
        this.addChild(this.serverInfoWrapper);
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.pingWidget != null && this.loadingIcon()) {
            this.pingWidget.icon().set(this.pingIcon());
        }
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        if (this.serverInfoWrapper == null) {
            return super.getContentHeight(type);
        }
        return this.serverInfoWrapper.bounds().getHeight(BoundsType.OUTER) + this.bounds().getVerticalOffset(type);
    }
    
    public final void connect() {
        this.connect(null);
    }
    
    public void connect(final String command) {
        this.connect(command, 0);
    }
    
    public void connect(final String command, final int delay) {
        if (this.serverData instanceof final ConnectableServerData connectableServerData) {
            connectableServerData.connect(command, delay);
            return;
        }
        ConnectableServerData.from(this.serverData).connect(command, delay);
    }
    
    protected boolean loadingIcon() {
        return this.serverInfo().getStatus() == ServerInfo.Status.LOADING;
    }
    
    @Override
    public int getListIndex() {
        return this.listIndex;
    }
    
    protected boolean display() {
        return true;
    }
    
    protected void startInitialize(final Parent parent) {
    }
    
    protected void finishInitialize(final Parent parent, final FlexibleContentWidget content, final boolean display) {
    }
    
    protected ServerInfo serverInfo() {
        return this.serverInfo;
    }
    
    public IconWidget getIconWidget() {
        return this.serverIconWidget;
    }
    
    public T serverData() {
        return this.serverData;
    }
    
    protected Component serverName() {
        return Component.text(this.serverData.getName());
    }
    
    protected IconWidget serverIconWidget(final boolean loading) {
        final Icon completable = Icon.completable(this.serverInfo.getIcon());
        completable.resolution(64, 64);
        return new IconWidget(completable);
    }
    
    protected Component description(final ServerInfo.Status forcedStatus) {
        final ServerInfo serverInfo = this.serverInfo();
        Component description = serverInfo.getDescription();
        if (forcedStatus != null || description == null) {
            final ServerInfo.Status status = (forcedStatus != null) ? forcedStatus : serverInfo.getStatus();
            switch (status) {
                case LOADING: {
                    description = Component.translatable("labymod.activity.multiplayer.server.status.pinging", NamedTextColor.GRAY);
                    break;
                }
                case CANNOT_CONNECT: {
                    description = Component.translatable("labymod.activity.multiplayer.server.status.cannot_connect", NamedTextColor.DARK_RED);
                    break;
                }
                case UNKNOWN_HOST: {
                    description = Component.translatable("labymod.activity.multiplayer.server.status.cannot_resolve", NamedTextColor.DARK_RED);
                    break;
                }
            }
            if (description != null) {
                description = description.append(Component.text("\n "));
            }
        }
        if (description == null) {
            description = Component.text("");
        }
        description = this.mergeStyleRecursive(description, Style.style(NamedTextColor.GRAY));
        return description;
    }
    
    protected ComponentWidget statusWidget() {
        Component component = Component.empty();
        final ServerInfo serverInfo = this.serverInfo();
        if (serverInfo.getStatus() == ServerInfo.Status.SUCCESS) {
            component = ((BaseComponent<Component>)Component.empty().append(Component.text(serverInfo.getPlayerCount(), NamedTextColor.GRAY)).append(Component.text("/", NamedTextColor.DARK_GRAY))).append(Component.text(serverInfo.getMaxPlayers(), NamedTextColor.GRAY));
        }
        if (serverInfo.getStatus() == ServerInfo.Status.WRONG_VERSION) {
            component = Component.text(serverInfo.getProtocolInfo(), NamedTextColor.RED);
        }
        final ComponentWidget widget = ComponentWidget.component(component);
        final ServerInfo.Player[] onlinePlayers = serverInfo.getOnlinePlayers();
        if (onlinePlayers != null && onlinePlayers.length != 0) {
            final List<RenderableComponent> components = new ArrayList<RenderableComponent>();
            for (final ServerInfo.Player player : onlinePlayers) {
                components.add(RenderableComponent.of(Component.text(player.getName())));
            }
            widget.setHoverRenderableComponent(RenderableComponent.realignedMerge(components));
        }
        return widget;
    }
    
    protected IconWidget pingWidget() {
        final IconWidget widget = new IconWidget(this.pingIcon());
        widget.addId("server-ping");
        final ServerInfo serverInfo = this.serverInfo();
        final ServerInfo.Status status = serverInfo.getStatus();
        if (status == ServerInfo.Status.WRONG_VERSION) {
            widget.setHoverComponent(Component.translatable("labymod.activity.multiplayer.server.status.incompatible_version", new Component[0]));
        }
        else if (status == ServerInfo.Status.LOADING) {
            widget.setHoverComponent(Component.translatable("labymod.activity.multiplayer.server.status.pinging", new Component[0]));
        }
        else if (status == ServerInfo.Status.SUCCESS && serverInfo.getPing() != -1) {
            widget.setHoverComponent(Component.text(serverInfo.getPing() + "ms"));
        }
        else {
            widget.setHoverComponent(Component.translatable("labymod.activity.multiplayer.server.status.no_connection", new Component[0]));
        }
        return widget;
    }
    
    private Icon pingIcon() {
        if (this.loadingIcon()) {
            int index = (int)(TimeUtil.getMillis() / 100L + this.listIndex * 2L & 0x7L);
            if (index > 4) {
                index = 8 - index;
            }
            return ServerInfoWidget.PING_ICON_REGISTRY.icon(PingType.LOADING_PING, index);
        }
        final ServerInfo serverInfo = this.serverInfo();
        final ServerInfo.Status status = serverInfo.getStatus();
        if (status == ServerInfo.Status.SUCCESS && serverInfo.getPing() != -1) {
            return ServerInfoWidget.PING_ICON_REGISTRY.icon(PingType.SERVER_PING, serverInfo.getPing());
        }
        return ServerInfoWidget.PING_ICON_REGISTRY.icon(PingType.ERROR);
    }
    
    private Component mergeStyleRecursive(final Component component, final Style style) {
        return ComponentUtils.mergeStyleRecursive(component, style, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
    }
    
    public void updateServerInfo(@NotNull final ServerInfo serverInfo) {
        Objects.requireNonNull(serverInfo, "Server info cannot be null");
        this.serverInfo = serverInfo;
        if (this.initialized) {
            this.reInitialize();
        }
    }
    
    @Override
    public String toString() {
        return this.serverInfo.getName();
    }
    
    static {
        PING_ICON_REGISTRY = Laby.references().pingIconRegistry();
    }
    
    public enum Movable
    {
        NO, 
        UP, 
        DOWN, 
        ALL, 
        SWAP, 
        ADD;
    }
}
