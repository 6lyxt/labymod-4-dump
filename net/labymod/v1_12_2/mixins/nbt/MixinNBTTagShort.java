// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagShort;

@Mixin({ gl.class })
public abstract class MixinNBTTagShort implements NBTTagShort
{
    @Shadow
    public abstract short f();
    
    @Override
    public Short value() {
        return this.f();
    }
}
