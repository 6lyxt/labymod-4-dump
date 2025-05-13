// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.blaze3d.program;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface StandardBlaze3DRenderTypes
{
    default Blaze3DRenderType entityTranslucent(final ResourceLocation location) {
        return this.entityTranslucent(location, true);
    }
    
    Blaze3DRenderType entityTranslucent(final ResourceLocation p0, final boolean p1);
    
    default Blaze3DRenderType legacySnoxhEyes() {
        throw new IllegalStateException();
    }
}
