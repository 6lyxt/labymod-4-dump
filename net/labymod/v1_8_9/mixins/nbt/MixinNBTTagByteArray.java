// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagByteArray;

@Mixin({ dl.class })
public abstract class MixinNBTTagByteArray implements NBTTagByteArray
{
    @Shadow
    public abstract byte[] c();
    
    @Override
    public byte[] value() {
        return this.c();
    }
}
