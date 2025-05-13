// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.nbt;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagByte;

@Implements({ @Interface(iface = NBTTagByte.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ ty.class })
public abstract class MixinByteTag implements NBTTagByte
{
    @Shadow
    public abstract byte shadow$n();
    
    @Intrinsic
    public Byte labyMod$value() {
        return this.shadow$n();
    }
}
