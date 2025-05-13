// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagFloat;

@Mixin({ dr.class })
public abstract class MixinNBTTagFloat implements NBTTagFloat
{
    @Shadow
    public abstract float h();
    
    @Override
    public Float value() {
        return this.h();
    }
}
