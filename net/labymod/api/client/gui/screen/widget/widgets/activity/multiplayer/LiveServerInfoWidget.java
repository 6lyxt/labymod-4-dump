// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.ServerData;

@AutoWidget
public class LiveServerInfoWidget<T extends ServerData> extends ServerInfoWidget<T>
{
    private static final int UPDATE_INTERVAL_TICKS = 100;
    private final ServerInfoCache<T> cache;
    private int tick;
    
    public LiveServerInfoWidget(@NotNull final T serverData) {
        super(serverData);
        this.cache = new ServerInfoCache<T>(serverData, cache -> this.updateServerInfo(cache.serverInfo()));
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.tick++ % 100 == 0) {
            this.cache.update();
        }
    }
}
