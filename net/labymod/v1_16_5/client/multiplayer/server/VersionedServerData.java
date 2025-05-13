// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.multiplayer.server;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;

public class VersionedServerData extends dwz
{
    private final ServerData serverData;
    
    public VersionedServerData(final ServerData serverData) {
        super(serverData.getName(), serverData.actualAddress().toString(), serverData.isLan());
        if (serverData instanceof ConnectableServerData) {
            this.a(of(((ConnectableServerData)serverData).resourcePackStatus()));
        }
        this.serverData = serverData;
    }
    
    public void a(@NotNull final dwz.a packStatus) {
        super.a(packStatus);
        if (this.serverData instanceof final StorageServerData storageServerData) {
            storageServerData.setResourcePackStatus(of(packStatus));
        }
    }
    
    public ServerData getServerData() {
        return this.serverData;
    }
    
    public static dwz.a of(final ServerResourcePackStatus resourcePackStatus) {
        switch (resourcePackStatus) {
            case ENABLED: {
                return dwz.a.a;
            }
            case DISABLED: {
                return dwz.a.b;
            }
            default: {
                return dwz.a.c;
            }
        }
    }
    
    public static ServerResourcePackStatus of(final dwz.a resourceMode) {
        switch (resourceMode) {
            case a: {
                return ServerResourcePackStatus.ENABLED;
            }
            case b: {
                return ServerResourcePackStatus.DISABLED;
            }
            default: {
                return ServerResourcePackStatus.PROMPT;
            }
        }
    }
}
