// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world.chunk;

import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.event.Event;

public class LightUpdateEvent implements Event
{
    private final LightType type;
    private final IntVector3 blockPosition;
    
    public LightUpdateEvent(final LightType type, final IntVector3 blockPosition) {
        this.type = type;
        this.blockPosition = blockPosition;
    }
    
    public LightType getType() {
        return this.type;
    }
    
    public IntVector3 getBlockPosition() {
        return this.blockPosition;
    }
}
