// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import net.labymod.v1_12_2.nbt.VersionedNBTTagLongArray;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagLongArray;

@Mixin(value = { VersionedNBTTagLongArray.class }, remap = false)
public abstract class MixinNBTTagLongArray implements NBTTagLongArray
{
    @Shadow
    public abstract long[] getLongArray();
    
    @Override
    public long[] value() {
        return this.getLongArray();
    }
}
