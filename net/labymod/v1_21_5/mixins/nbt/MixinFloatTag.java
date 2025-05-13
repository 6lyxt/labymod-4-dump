// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.nbt;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagFloat;

@Implements({ @Interface(iface = NBTTagFloat.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ ud.class })
public abstract class MixinFloatTag implements NBTTagFloat
{
    @Shadow
    public abstract float shadow$n();
    
    @Intrinsic
    public Float labyMod$value() {
        return this.shadow$n();
    }
}
