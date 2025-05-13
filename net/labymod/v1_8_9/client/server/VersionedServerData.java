// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.server;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;

public class VersionedServerData extends bde
{
    private final ServerData serverData;
    
    public VersionedServerData(final ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), serverData.isLan());
        if (serverData instanceof ConnectableServerData) {
            this.a(of(((ConnectableServerData)serverData).resourcePackStatus()));
        }
        this.serverData = serverData;
    }
    
    public void a(final bde.a resourceMode) {
        super.a(resourceMode);
        if (this.serverData instanceof final StorageServerData storageServerData) {
            storageServerData.setResourcePackStatus(of(resourceMode));
        }
    }
    
    public ServerData getServerData() {
        return this.serverData;
    }
    
    public static bde.a of(final ServerResourcePackStatus resourcePackStatus) {
        switch (resourcePackStatus) {
            case ENABLED: {
                return bde.a.a;
            }
            case DISABLED: {
                return bde.a.b;
            }
            default: {
                return bde.a.c;
            }
        }
    }
    
    public static ServerResourcePackStatus of(final bde.a resourceMode) {
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
