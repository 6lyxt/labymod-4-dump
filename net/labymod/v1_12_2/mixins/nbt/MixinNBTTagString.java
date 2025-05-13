// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.nbt;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagString;

@Mixin({ gm.class })
public abstract class MixinNBTTagString implements NBTTagString
{
    @Shadow
    public abstract String c_();
    
    @Nullable
    @Override
    public String value() {
        return this.c_();
    }
}
