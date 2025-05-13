// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagInt;

@Mixin({ rb.class })
public abstract class MixinIntTag implements NBTTagInt
{
    @Shadow
    public abstract int g();
    
    @Override
    public Integer value() {
        return this.g();
    }
}
