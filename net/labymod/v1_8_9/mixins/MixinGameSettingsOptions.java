// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.GameSettingsOptionsAccessor;

@Mixin({ avh.a.class })
public class MixinGameSettingsOptions implements GameSettingsOptionsAccessor
{
    @Shadow
    private float X;
    @Shadow
    @Final
    private float W;
    
    @Override
    public float getMinValue() {
        return this.X;
    }
    
    @Override
    public float getStep() {
        return this.W;
    }
}
