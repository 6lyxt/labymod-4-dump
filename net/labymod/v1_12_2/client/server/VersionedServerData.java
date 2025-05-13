// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.server;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;

public class VersionedServerData extends bse
{
    private final ServerData serverData;
    
    public VersionedServerData(final ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), serverData.isLan());
        if (serverData instanceof ConnectableServerData) {
            this.a(of(((ConnectableServerData)serverData).resourcePackStatus()));
        }
        this.serverData = serverData;
    }
    
    public void a(final bse.a resourceMode) {
        super.a(resourceMode);
        if (this.serverData instanceof final StorageServerData storageServerData) {
            storageServerData.setResourcePackStatus(of(resourceMode));
        }
    }
    
    public ServerData getServerData() {
        return this.serverData;
    }
    
    public static bse.a of(final ServerResourcePackStatus resourcePackStatus) {
        switch (resourcePackStatus) {
            case ENABLED: {
                return bse.a.a;
            }
            case DISABLED: {
                return bse.a.b;
            }
            default: {
                return bse.a.c;
            }
        }
    }
    
    public static ServerResourcePackStatus of(final bse.a resourceMode) {
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
