// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagFloat;

@Mixin({ qz.class })
public abstract class MixinFloatTag implements NBTTagFloat
{
    @Shadow
    public abstract float k();
    
    @Override
    public Float value() {
        return this.k();
    }
}
