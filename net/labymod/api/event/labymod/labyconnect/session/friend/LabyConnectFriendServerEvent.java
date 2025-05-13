// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.friend;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;

public class LabyConnectFriendServerEvent extends LabyConnectFriendEvent
{
    private final ServerInfo server;
    private final ServerInfo previousServer;
    
    public LabyConnectFriendServerEvent(final LabyConnect api, final Friend friend, final ServerInfo server) {
        this(api, friend, server, null);
    }
    
    public LabyConnectFriendServerEvent(final LabyConnect api, final Friend friend, final ServerInfo server, final ServerInfo previousServer) {
        super(api, friend);
        this.server = server;
        this.previousServer = previousServer;
    }
    
    public ServerInfo serverInfo() {
        return this.server;
    }
    
    @Nullable
    public ServerInfo getPreviousServer() {
        return this.previousServer;
    }
}
