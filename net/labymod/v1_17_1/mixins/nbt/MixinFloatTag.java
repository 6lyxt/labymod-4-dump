// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagFloat;

@Mixin({ nd.class })
public abstract class MixinFloatTag implements NBTTagFloat
{
    @Shadow
    public abstract float j();
    
    @Override
    public Float value() {
        return this.j();
    }
}
