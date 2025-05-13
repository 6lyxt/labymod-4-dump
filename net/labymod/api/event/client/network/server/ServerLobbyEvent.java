// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;

public class ServerLobbyEvent extends ServerEvent
{
    private final String lobbyName;
    
    public ServerLobbyEvent(final ConnectableServerData serverData, final String lobbyName) {
        super(serverData);
        this.lobbyName = lobbyName;
    }
    
    public String getLobbyName() {
        return this.lobbyName;
    }
}
