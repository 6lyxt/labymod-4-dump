// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagInt;

@Mixin({ po.class })
public abstract class MixinIntTag implements NBTTagInt
{
    @Shadow
    public abstract int f();
    
    @Override
    public Integer value() {
        return this.f();
    }
}
