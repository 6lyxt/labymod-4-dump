// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagByte;

@Mixin({ fx.class })
public abstract class MixinNBTTagByte implements NBTTagByte
{
    @Shadow
    public abstract byte g();
    
    @Override
    public Byte value() {
        return this.g();
    }
}
