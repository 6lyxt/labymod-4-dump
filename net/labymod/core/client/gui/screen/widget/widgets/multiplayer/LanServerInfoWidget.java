// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.network.server.ServerInfo;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;

@AutoWidget
public class LanServerInfoWidget extends ServerInfoWidget<ConnectableServerData>
{
    public LanServerInfoWidget(@NotNull final ServerInfoCache<ConnectableServerData> serverInfoCache) {
        super(serverInfoCache.serverInfo(), serverInfoCache.serverInfo());
        serverInfoCache.setCallback(this::updateServerInfo);
    }
    
    public void updateServerInfo(final ServerInfoCache<ConnectableServerData> serverInfoCache) {
        final ServerInfo serverInfo = serverInfoCache.serverInfo();
        this.updateServerInfo(serverInfo);
    }
}
