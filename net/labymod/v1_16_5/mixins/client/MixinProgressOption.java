// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.ProgressOptionAccessor;

@Mixin({ dkf.class })
public class MixinProgressOption implements ProgressOptionAccessor
{
    @Shadow
    @Final
    protected float Y;
    
    @Override
    public float getSteps() {
        return this.Y;
    }
}
