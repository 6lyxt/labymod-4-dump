// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Event;

public class DimensionChangeEvent implements Event
{
    private final ResourceLocation fromDimension;
    private final ResourceLocation toDimension;
    
    public DimensionChangeEvent(@NotNull final ResourceLocation fromDimension, @NotNull final ResourceLocation toDimension) {
        this.fromDimension = fromDimension;
        this.toDimension = toDimension;
    }
    
    @NotNull
    public ResourceLocation fromDimension() {
        return this.fromDimension;
    }
    
    @NotNull
    public ResourceLocation toDimension() {
        return this.toDimension;
    }
}
