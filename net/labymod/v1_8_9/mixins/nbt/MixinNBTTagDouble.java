// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagDouble;

@Mixin({ dp.class })
public abstract class MixinNBTTagDouble implements NBTTagDouble
{
    @Shadow
    public abstract double g();
    
    @Override
    public Double value() {
        return this.g();
    }
}
