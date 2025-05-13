// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.nbt;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Shadow;
import java.io.DataOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.NBTTag;

@Implements({ @Interface(iface = NBTTag.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ ri.class })
public interface MixinTag extends NBTTag
{
    @Shadow
    void a(final DataOutput p0);
    
    @Intrinsic
    default void labyMod$write(@NotNull final DataOutput output) {
        this.a(output);
    }
}
