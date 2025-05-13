// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.nbt;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagString;

@Mixin({ ms.class })
public abstract class MixinStringTag implements NBTTagString
{
    @Shadow
    public abstract String f_();
    
    @Nullable
    @Override
    public String value() {
        return this.f_();
    }
}
