// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.playerinfo;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Event;

public class PlayerInfoRemoveEvent implements Event
{
    private final NetworkPlayerInfo playerInfo;
    
    public PlayerInfoRemoveEvent(@NotNull final NetworkPlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }
    
    @NotNull
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }
}
