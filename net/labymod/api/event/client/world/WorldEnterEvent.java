// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Event;

public class WorldEnterEvent implements Event
{
    private final Type type;
    
    public WorldEnterEvent(@NotNull final Type type) {
        this.type = type;
    }
    
    @NotNull
    public Type type() {
        return this.type;
    }
    
    public enum Type
    {
        SINGLEPLAYER, 
        MULTIPLAYER;
    }
}
