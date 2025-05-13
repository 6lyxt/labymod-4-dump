// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.component.Component;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class DirectConnectServerInfoWidget extends LiveLabyNetServerInfoWidget
{
    public DirectConnectServerInfoWidget(@NotNull final StorageServerData serverData, @NotNull final LabyNetServerInfoCache<StorageServerData> cache) {
        super(serverData, cache);
        this.setFriendHeadsEnabled(false);
    }
    
    @Override
    protected Component serverName() {
        final ConnectableServerData connectableServerData = ((ServerInfoWidget<ConnectableServerData>)this).serverData();
        String name = connectableServerData.getName();
        final ServerAddress address = connectableServerData.address();
        if (address.getPort() != 25565) {
            name = name + ":" + address.getPort();
        }
        return Component.text(name);
    }
    
    public void setProgressable(final boolean progressable) {
        if (progressable) {
            this.startRefreshing();
        }
        else {
            this.stopRefreshing();
        }
    }
    
    public float getProgress() {
        final long nextUpdateMillis = this.nextUpdateMillis;
        if (nextUpdateMillis == 0L) {
            return 0.0f;
        }
        final long millis = TimeUtil.getMillis();
        if (nextUpdateMillis <= millis) {
            return 1.0f;
        }
        return (millis - (nextUpdateMillis - 5000L)) / 5000.0f;
    }
}
