// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagDouble;

@Mixin({ nb.class })
public abstract class MixinDoubleTag implements NBTTagDouble
{
    @Shadow
    public abstract double i();
    
    @Override
    public Double value() {
        return this.i();
    }
}
