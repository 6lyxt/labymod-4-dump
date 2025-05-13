// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.nbt;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.tags.NBTTagString;

@Implements({ @Interface(iface = NBTTagString.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ uy.class })
public abstract class MixinStringTag implements NBTTagString
{
    @Shadow
    public abstract String shadow$k();
    
    @Intrinsic
    @Nullable
    public String labyMod$value() {
        return this.shadow$k();
    }
}
