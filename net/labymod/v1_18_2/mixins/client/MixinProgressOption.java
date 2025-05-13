// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.ProgressOptionAccessor;

@Mixin({ dyz.class })
public class MixinProgressOption implements ProgressOptionAccessor
{
    @Shadow
    @Final
    protected float af;
    
    @Override
    public float getSteps() {
        return this.af;
    }
}
