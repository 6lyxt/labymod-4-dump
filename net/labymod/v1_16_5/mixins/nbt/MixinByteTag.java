// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagByte;

@Mixin({ mb.class })
public abstract class MixinByteTag implements NBTTagByte
{
    @Shadow
    public abstract byte h();
    
    @Override
    public Byte value() {
        return this.h();
    }
}
