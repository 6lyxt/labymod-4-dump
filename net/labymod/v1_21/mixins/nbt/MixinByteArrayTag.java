// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagByteArray;

@Mixin({ ty.class })
public abstract class MixinByteArrayTag implements NBTTagByteArray
{
    @Shadow
    public abstract byte[] e();
    
    @Override
    public byte[] value() {
        return this.e();
    }
}
