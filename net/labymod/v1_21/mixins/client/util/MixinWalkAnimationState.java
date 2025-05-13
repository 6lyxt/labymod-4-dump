// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.util;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21.client.util.WalkAnimationStateAccessor;

@Mixin({ bup.class })
public class MixinWalkAnimationState implements WalkAnimationStateAccessor
{
    @Shadow
    private float a;
    
    @Override
    public float getSpeedOld() {
        return this.a;
    }
}
