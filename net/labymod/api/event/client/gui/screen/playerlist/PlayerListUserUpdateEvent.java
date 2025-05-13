// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.playerlist;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.NetworkPlayerInfo;

public class PlayerListUserUpdateEvent extends PlayerListUpdateEvent
{
    private final NetworkPlayerInfo playerInfo;
    
    public PlayerListUserUpdateEvent(@NotNull final NetworkPlayerInfo playerInfo) {
        Objects.requireNonNull(playerInfo, "Player info cannot be null!");
        this.playerInfo = playerInfo;
    }
    
    @NotNull
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }
}
