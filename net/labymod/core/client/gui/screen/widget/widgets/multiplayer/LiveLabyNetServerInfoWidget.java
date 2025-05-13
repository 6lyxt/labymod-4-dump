// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.ServerInfoCache;
import java.util.function.Consumer;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.storage.StorageServerData;

@AutoWidget
public class LiveLabyNetServerInfoWidget extends LabyNetServerInfoWidget<StorageServerData>
{
    protected static final long UPDATE_INTERVAL_MILLIS = 5000L;
    protected long nextUpdateMillis;
    
    public LiveLabyNetServerInfoWidget(@NotNull final StorageServerData serverData, @NotNull final LabyNetServerInfoCache<StorageServerData> cache) {
        super(serverData, cache);
    }
    
    public LiveLabyNetServerInfoWidget(@NotNull final StorageServerData serverData) {
        super(serverData, new LabyNetServerInfoCache<StorageServerData>(serverData, null));
        this.cache().update();
        this.setDefaultCallback();
    }
    
    @Override
    protected boolean loadingIcon() {
        return this.serverInfo().getStatus() == ServerInfo.Status.LOADING;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.nextUpdateMillis != 0L && this.nextUpdateMillis <= TimeUtil.getMillis()) {
            this.stopRefreshing();
            this.cache().update();
        }
    }
    
    protected final void stopRefreshing() {
        this.nextUpdateMillis = 0L;
    }
    
    protected final void startRefreshing() {
        this.nextUpdateMillis = TimeUtil.getMillis() + 5000L;
    }
    
    public void setDefaultCallback() {
        this.cache().setCallback(cache -> {
            final ServerInfo serverInfo = cache.serverInfo();
            if (serverInfo.getStatus() != ServerInfo.Status.LOADING) {
                this.startRefreshing();
            }
            this.updateServerInfo(serverInfo);
        });
    }
}
