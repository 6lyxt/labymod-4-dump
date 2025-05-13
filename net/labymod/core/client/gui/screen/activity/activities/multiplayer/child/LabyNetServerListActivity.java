// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import java.util.Iterator;
import java.util.List;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.network.server.ServerData;

public abstract class LabyNetServerListActivity<T extends ServerData, W extends ServerEntryWidget> extends ServerListActivity<T, W>
{
    private static final Logging LOGGER;
    private long nextUpdateMillis;
    
    protected LabyNetServerListActivity(final String identifier, final TextFieldWidget searchField) {
        super(identifier, searchField);
    }
    
    @Override
    protected void refresh(final boolean visible) {
        this.nextUpdateMillis = 0L;
        this.lastRefreshTime = TimeUtil.getMillis();
        final List<W> children = this.serverListWidget.getChildren();
        for (final W child : children) {
            if (child.isDragging()) {
                return;
            }
        }
        for (int size = children.size(), index = 0; index < size; ++index) {
            try {
                final W child2 = children.get(index);
                if (child2 != null && !child2.isDragging()) {
                    if (child2 instanceof LabyNetServerInfoWidget) {
                        final LabyNetServerInfoWidget<?> serverInfo = (LabyNetServerInfoWidget<?>)child2;
                        serverInfo.refresh(visible);
                    }
                }
            }
            catch (final Throwable throwable) {
                LabyNetServerListActivity.LOGGER.debug("Error refreshing server list", throwable);
            }
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        final ServerListConfig serverListConfig = LabyConfigProvider.INSTANCE.get().multiplayer().serverList();
        if (!serverListConfig.liveServerList().get()) {
            this.nextUpdateMillis = 0L;
            return;
        }
        if (this.nextUpdateMillis == 0L) {
            this.nextUpdateMillis = TimeUtil.getMillis() + serverListConfig.cooldown().get() * 1000;
            return;
        }
        if (TimeUtil.getMillis() >= this.nextUpdateMillis) {
            this.refresh(false);
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
