// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagLong;

@Mixin({ dv.class })
public abstract class MixinNBTTagLong implements NBTTagLong
{
    @Shadow
    public abstract long c();
    
    @Override
    public Long value() {
        return this.c();
    }
}
