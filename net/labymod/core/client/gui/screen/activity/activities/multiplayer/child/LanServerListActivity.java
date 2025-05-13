// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LanServerInfoWidget;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.network.server.ServerInfoCache;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.network.server.ConnectableServerData;

@AutoActivity
@Link("activity/multiplayer/lan-server-list.lss")
public class LanServerListActivity extends ServerListActivity<ConnectableServerData, ServerInfoWidget<ConnectableServerData>>
{
    private final Map<ConnectableServerData, ServerInfoCache<ConnectableServerData>> lanServers;
    private Runnable serverRemoveCallback;
    private int updateTick;
    
    public LanServerListActivity() {
        super("lan", null);
        this.lanServers = new HashMap<ConnectableServerData, ServerInfoCache<ConnectableServerData>>();
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.updateTick++ == 50) {
            this.updateTick = 0;
            this.refresh(false);
        }
    }
    
    @Override
    protected void fillServerList(final VerticalListWidget<ServerInfoWidget<ConnectableServerData>> serverListWidget, final String searchQuery) {
        for (final ServerInfoCache<ConnectableServerData> serverCache : this.lanServers.values()) {
            this.addServerWidget(serverCache, false);
        }
    }
    
    private void addServerWidget(final ServerInfoCache<ConnectableServerData> cache, final boolean initialize) {
        final LanServerInfoWidget serverInfoWidget = new LanServerInfoWidget(cache);
        serverInfoWidget.setMovable(ServerInfoWidget.Movable.ADD, movable -> serverInfoWidget.serverData().connect());
        if (initialize) {
            this.serverListWidget.addChildInitialized((W)serverInfoWidget);
        }
        else {
            this.serverListWidget.addChild((W)serverInfoWidget);
        }
    }
    
    private void removeServerWidget(final ServerInfoCache<ConnectableServerData> cache) {
        Widget widget = null;
        final List<Widget> children = this.serverListWidget.findChildrenIf(widget -> widget.serverData().address().equals(cache.serverAddress()));
        final Iterator<Widget> iterator = children.iterator();
        while (iterator.hasNext()) {
            widget = iterator.next();
            this.serverListWidget.removeChild((W)widget);
        }
        if (this.serverRemoveCallback != null) {
            this.serverRemoveCallback.run();
        }
    }
    
    public void removeLanServer(final ConnectableServerData data) {
        final ServerInfoCache<ConnectableServerData> cache = this.lanServers.remove(data);
        if (cache != null) {
            this.labyAPI.minecraft().executeOnRenderThread(() -> this.removeServerWidget(cache));
        }
    }
    
    @Override
    protected void refresh(final boolean visible) {
        for (final ServerInfoCache<ConnectableServerData> cache : this.lanServers.values()) {
            cache.update();
        }
    }
    
    @Override
    protected void fillButtonContainer(final FlexibleContentWidget container) {
        container.addFlexibleContent(this.joinButton);
    }
    
    public void addLanServer(final ConnectableServerData serverData, final Runnable runnable) {
        if (this.lanServers.containsKey(serverData)) {
            return;
        }
        final ServerInfoCache<ConnectableServerData> cache = new ServerInfoCache<ConnectableServerData>(serverData, null);
        this.lanServers.put(serverData, cache);
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            if (((Document)this.document).isInitialized()) {
                this.addServerWidget(cache, true);
            }
            runnable.run();
            return;
        });
        cache.setTimeout(2500);
        cache.update();
    }
    
    public Map<ConnectableServerData, ServerInfoCache<ConnectableServerData>> getLanServers() {
        return this.lanServers;
    }
    
    public void setServerRemoveCallback(final Runnable serverRemoveCallback) {
        this.serverRemoveCallback = serverRemoveCallback;
    }
}
