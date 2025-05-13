// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagDouble;

@Mixin({ fz.class })
public abstract class MixinNBTTagDouble implements NBTTagDouble
{
    @Shadow
    public abstract double h();
    
    @Override
    public Double value() {
        return this.h();
    }
}
