// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer;

import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.storage.StorageServerData;
import java.util.function.Consumer;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.client.network.server.ServerData;

public class LabyNetServerInfoCache<T extends ServerData> extends ServerInfoCache<T>
{
    private static final LabyNetController LABY_NET_CONTROLLER;
    private ServerGroup serverGroup;
    private int sortingValue;
    
    public LabyNetServerInfoCache(final T serverData, final Consumer<ServerInfoCache<T>> callback) {
        super(serverData, callback);
    }
    
    public void setName(final String name) {
        ((StorageServerData)this.serverData).setName(name);
    }
    
    public ServerGroup getServerGroup() {
        return this.serverGroup;
    }
    
    public int getSortingValue() {
        return this.sortingValue;
    }
    
    public void setSortingValue(final int sortingValue) {
        this.sortingValue = sortingValue;
    }
    
    public void setAddress(final String address) {
        if (this.serverData instanceof final StorageServerData storageServerData) {
            storageServerData.setServerAddress(address);
            this.refresh(true);
        }
    }
    
    public void apply(final ServerData serverData) {
        this.setName(serverData.getName());
        if (!this.serverData.address().equals(serverData.address())) {
            ((StorageServerData)this.serverData).setServerAddress(serverData.address());
        }
    }
    
    @Override
    public void refresh(final boolean callback) {
        final ServerAddress address = this.serverAddress();
        if (address != null) {
            this.serverGroup = LabyNetServerInfoCache.LABY_NET_CONTROLLER.getServerByIp(address).orElse(null);
            super.refresh(callback);
        }
    }
    
    @Override
    public void setServerInfo(final ServerInfo serverInfo) {
        super.setServerInfo(serverInfo);
        if (serverInfo.getRawIcon() != null && this.serverData instanceof StorageServerData) {
            ((StorageServerData)this.serverData).setIconBase64(serverInfo.getRawIcon().replace("data:image/png;base64,", ""));
        }
    }
    
    static {
        LABY_NET_CONTROLLER = Laby.references().labyNetController();
    }
}
