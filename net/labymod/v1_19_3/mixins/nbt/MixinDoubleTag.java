// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagDouble;

@Mixin({ qq.class })
public abstract class MixinDoubleTag implements NBTTagDouble
{
    @Shadow
    public abstract double j();
    
    @Override
    public Double value() {
        return this.j();
    }
}
