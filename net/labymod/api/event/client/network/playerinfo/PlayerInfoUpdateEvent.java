// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.playerinfo;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Event;

public class PlayerInfoUpdateEvent implements Event
{
    private final NetworkPlayerInfo playerInfo;
    private final UpdateType type;
    
    public PlayerInfoUpdateEvent(@NotNull final NetworkPlayerInfo playerInfo, @NotNull final UpdateType type) {
        this.playerInfo = playerInfo;
        this.type = type;
    }
    
    @NotNull
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }
    
    @NotNull
    public UpdateType type() {
        return this.type;
    }
    
    public enum UpdateType
    {
        GAME_MODE, 
        PING, 
        DISPLAY_NAME, 
        UPDATE_LISTED, 
        UPDATE_LIST_ORDER, 
        UPDATE_HAT;
    }
}
