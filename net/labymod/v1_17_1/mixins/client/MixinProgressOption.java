// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.accessor.gui.ProgressOptionAccessor;

@Mixin({ dvv.class })
public class MixinProgressOption implements ProgressOptionAccessor
{
    @Shadow
    @Final
    protected float aa;
    
    @Override
    public float getSteps() {
        return this.aa;
    }
}
