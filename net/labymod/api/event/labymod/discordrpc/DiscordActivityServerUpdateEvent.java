// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.discordrpc;

import org.jetbrains.annotations.ApiStatus;
import java.util.Objects;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ServerData;

public class DiscordActivityServerUpdateEvent extends DiscordActivityUpdateEvent
{
    private final ServerData serverData;
    
    @ApiStatus.Internal
    public DiscordActivityServerUpdateEvent(@NotNull final ServerData serverData, @NotNull final DiscordActivity activity) {
        super(activity);
        Objects.requireNonNull(serverData, "Server data cannot be null!");
        this.serverData = serverData;
    }
    
    @NotNull
    public ServerData serverData() {
        return this.serverData;
    }
}
