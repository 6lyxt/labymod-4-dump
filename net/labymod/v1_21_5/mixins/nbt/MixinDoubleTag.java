// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.nbt;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagDouble;

@Implements({ @Interface(iface = NBTTagDouble.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ ub.class })
public abstract class MixinDoubleTag implements NBTTagDouble
{
    @Shadow
    public abstract double shadow$n();
    
    @Intrinsic
    public Double labyMod$value() {
        return this.shadow$n();
    }
}
