// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.GameSettingsOptionsAccessor;

@Mixin({ bid.a.class })
public class MixinGameSettingsOptions implements GameSettingsOptionsAccessor
{
    @Shadow
    private float S;
    @Shadow
    @Final
    private float R;
    
    @Override
    public float getMinValue() {
        return this.S;
    }
    
    @Override
    public float getStep() {
        return this.R;
    }
}
