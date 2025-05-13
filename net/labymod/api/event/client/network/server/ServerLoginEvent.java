// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.component.Component;

public class ServerLoginEvent extends ServerEvent
{
    private Component disconnectReason;
    private final CookieStorage cookieStorage;
    
    public ServerLoginEvent(@NotNull final ConnectableServerData serverData) {
        this(serverData, null);
    }
    
    public ServerLoginEvent(@NotNull final ConnectableServerData serverData, final CookieStorage cookieStorage) {
        super(serverData);
        this.cookieStorage = cookieStorage;
    }
    
    @Nullable
    public Component getDisconnectReason() {
        return this.disconnectReason;
    }
    
    public void setDisconnectReason(@Nullable final Component disconnectReason) {
        this.disconnectReason = disconnectReason;
    }
    
    public Optional<CookieStorage> cookieStorage() {
        return Optional.ofNullable(this.cookieStorage);
    }
}
