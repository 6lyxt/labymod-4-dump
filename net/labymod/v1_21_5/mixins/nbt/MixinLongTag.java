// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.nbt;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagLong;

@Implements({ @Interface(iface = NBTTagLong.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ ui.class })
public abstract class MixinLongTag implements NBTTagLong
{
    @Shadow
    public abstract long shadow$n();
    
    @Intrinsic
    public Long labyMod$value() {
        return this.shadow$n();
    }
}
