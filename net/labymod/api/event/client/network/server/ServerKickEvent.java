// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.component.Component;

public class ServerKickEvent extends ServerEvent
{
    private final Context context;
    private Component reason;
    
    public ServerKickEvent(@NotNull final ConnectableServerData serverData, @NotNull final Component reason, @NotNull final Context context) {
        super(serverData);
        Objects.requireNonNull(reason, "Reason cannot be null!");
        Objects.requireNonNull(context, "Context cannot be null!");
        this.reason = reason;
        this.context = context;
    }
    
    @NotNull
    public Context context() {
        return this.context;
    }
    
    @NotNull
    public Component reason() {
        return this.reason;
    }
    
    public void setReason(@NotNull final Component reason) {
        Objects.requireNonNull(reason, "Reason cannot be null!");
        this.reason = reason;
    }
    
    public enum Context
    {
        PRE_LOGIN, 
        LOGIN, 
        PLAY;
    }
}
