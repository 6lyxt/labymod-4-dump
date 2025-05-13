// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.multiplayer.server;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;

public class VersionedServerData extends evb
{
    private final ServerData serverData;
    
    public VersionedServerData(final ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), serverData.isLan());
        if (serverData instanceof final ConnectableServerData connectableServerData) {
            this.a(of(connectableServerData.resourcePackStatus()));
        }
        this.serverData = serverData;
    }
    
    public void a(@NotNull final evb.b packStatus) {
        super.a(packStatus);
        final ServerData serverData = this.serverData;
        if (serverData instanceof final StorageServerData storageServerData) {
            storageServerData.setResourcePackStatus(of(packStatus));
        }
    }
    
    public ServerData getServerData() {
        return this.serverData;
    }
    
    public static evb.b of(final ServerResourcePackStatus resourcePackStatus) {
        return switch (resourcePackStatus) {
            case ENABLED -> evb.b.a;
            case DISABLED -> evb.b.b;
            default -> evb.b.c;
        };
    }
    
    public static ServerResourcePackStatus of(final evb.b resourceMode) {
        return switch (resourceMode) {
            case a -> ServerResourcePackStatus.ENABLED;
            case b -> ServerResourcePackStatus.DISABLED;
            default -> ServerResourcePackStatus.PROMPT;
        };
    }
}
