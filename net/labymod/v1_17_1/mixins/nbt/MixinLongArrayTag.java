// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagLongArray;

@Mixin({ nh.class })
public abstract class MixinLongArrayTag implements NBTTagLongArray
{
    @Shadow
    public abstract long[] f();
    
    @Override
    public long[] value() {
        return this.f();
    }
}
