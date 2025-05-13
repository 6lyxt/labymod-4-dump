// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.multiplayer.server;

import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.client.network.server.storage.StorageServerData;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.network.server.ServerData;

public class VersionedServerData extends gmd
{
    private final ServerData serverData;
    
    public VersionedServerData(final ServerData serverData) {
        super(serverData.getName(), serverData.address().toString(), MinecraftUtil.toMinecraft(serverData.type()));
        if (serverData instanceof final ConnectableServerData connectableServerData) {
            this.a(of(connectableServerData.resourcePackStatus()));
        }
        this.serverData = serverData;
    }
    
    public void a(@NotNull final gmd.a packStatus) {
        super.a(packStatus);
        final ServerData serverData = this.serverData;
        if (serverData instanceof final StorageServerData storageServerData) {
            storageServerData.setResourcePackStatus(of(packStatus));
        }
    }
    
    public ServerData getServerData() {
        return this.serverData;
    }
    
    public static gmd.a of(final ServerResourcePackStatus resourcePackStatus) {
        return switch (resourcePackStatus) {
            case ENABLED -> gmd.a.a;
            case DISABLED -> gmd.a.b;
            default -> gmd.a.c;
        };
    }
    
    public static ServerResourcePackStatus of(final gmd.a resourceMode) {
        return switch (resourceMode) {
            case a -> ServerResourcePackStatus.ENABLED;
            case b -> ServerResourcePackStatus.DISABLED;
            default -> ServerResourcePackStatus.PROMPT;
        };
    }
}
