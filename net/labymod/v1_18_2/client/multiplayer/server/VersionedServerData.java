// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.multiplayer.server;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;

public class VersionedServerData extends emx
{
    private final ServerData serverData;
    
    public VersionedServerData(final ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), serverData.isLan());
        if (serverData instanceof final ConnectableServerData connectableServerData) {
            this.a(of(connectableServerData.resourcePackStatus()));
        }
        this.serverData = serverData;
    }
    
    public void a(@NotNull final emx.a packStatus) {
        super.a(packStatus);
        final ServerData serverData = this.serverData;
        if (serverData instanceof final StorageServerData storageServerData) {
            storageServerData.setResourcePackStatus(of(packStatus));
        }
    }
    
    public ServerData getServerData() {
        return this.serverData;
    }
    
    public static emx.a of(final ServerResourcePackStatus resourcePackStatus) {
        return switch (resourcePackStatus) {
            case ENABLED -> emx.a.a;
            case DISABLED -> emx.a.b;
            default -> emx.a.c;
        };
    }
    
    public static ServerResourcePackStatus of(final emx.a resourceMode) {
        return switch (resourceMode) {
            case a -> ServerResourcePackStatus.ENABLED;
            case b -> ServerResourcePackStatus.DISABLED;
            default -> ServerResourcePackStatus.PROMPT;
        };
    }
}
