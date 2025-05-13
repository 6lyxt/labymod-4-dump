// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.phys.hit;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface HitResultController
{
    HitResult getResult();
    
    default boolean isCrosshairOverBlock() {
        return this.getResult() != null && this.getResult().type() == HitResult.HitType.BLOCK;
    }
}
