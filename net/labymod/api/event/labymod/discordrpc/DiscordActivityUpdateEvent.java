// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.discordrpc;

import org.jetbrains.annotations.ApiStatus;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.api.event.DefaultCancellable;

public class DiscordActivityUpdateEvent extends DefaultCancellable
{
    private final DiscordActivity originActivity;
    private DiscordActivity activity;
    
    @ApiStatus.Internal
    public DiscordActivityUpdateEvent(@NotNull final DiscordActivity activity) {
        Objects.requireNonNull(activity, "Discord activity cannot be null!");
        this.originActivity = activity;
        this.activity = activity;
    }
    
    @NotNull
    public DiscordActivity originActivity() {
        return this.originActivity;
    }
    
    @NotNull
    public DiscordActivity activity() {
        return this.activity;
    }
    
    public void setActivity(@NotNull final DiscordActivity activity) {
        Objects.requireNonNull(activity, "Activity cannot be null. Cancel the event instead.");
        this.activity = activity;
    }
}
