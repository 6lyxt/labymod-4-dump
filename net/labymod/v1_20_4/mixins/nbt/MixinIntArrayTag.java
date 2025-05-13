// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagIntArray;

@Mixin({ sr.class })
public abstract class MixinIntArrayTag implements NBTTagIntArray
{
    @Shadow
    public abstract int[] g();
    
    @Override
    public int[] value() {
        return this.g();
    }
}
