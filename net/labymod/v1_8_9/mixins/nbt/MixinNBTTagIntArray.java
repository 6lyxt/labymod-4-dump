// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagIntArray;

@Mixin({ ds.class })
public abstract class MixinNBTTagIntArray implements NBTTagIntArray
{
    @Shadow
    public abstract int[] c();
    
    @Override
    public int[] value() {
        return this.c();
    }
}
