// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagLong;

@Mixin({ vf.class })
public abstract class MixinLongTag implements NBTTagLong
{
    @Shadow
    public abstract long f();
    
    @Override
    public Long value() {
        return this.f();
    }
}
