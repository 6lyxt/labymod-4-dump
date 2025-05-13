// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;

public class ServerDisconnectEvent extends ServerEvent
{
    public ServerDisconnectEvent(@NotNull final ConnectableServerData serverData) {
        super(serverData);
    }
}
