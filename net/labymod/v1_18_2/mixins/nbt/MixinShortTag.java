// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagShort;

@Mixin({ oy.class })
public abstract class MixinShortTag implements NBTTagShort
{
    @Shadow
    public abstract short g();
    
    @Override
    public Short value() {
        return this.g();
    }
}
