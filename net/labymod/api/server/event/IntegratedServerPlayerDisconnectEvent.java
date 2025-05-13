// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.server.event;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public class IntegratedServerPlayerDisconnectEvent implements Event
{
    private final Phase phase;
    private final GameProfile profile;
    
    public IntegratedServerPlayerDisconnectEvent(@NotNull final Phase phase, @NotNull final GameProfile profile) {
        this.phase = phase;
        this.profile = profile;
    }
    
    @NotNull
    public Phase phase() {
        return this.phase;
    }
    
    @NotNull
    public GameProfile profile() {
        return this.profile;
    }
}
