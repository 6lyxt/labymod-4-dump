// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public class WorldLoadEvent implements Event
{
    private final Phase phase;
    
    public WorldLoadEvent(final Phase phase) {
        this.phase = phase;
    }
    
    public Phase phase() {
        return this.phase;
    }
}
