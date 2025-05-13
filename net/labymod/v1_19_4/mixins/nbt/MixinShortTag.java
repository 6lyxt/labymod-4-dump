// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.nbt;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagShort;

@Mixin({ rs.class })
public abstract class MixinShortTag implements NBTTagShort
{
    @Shadow
    public abstract short h();
    
    @Override
    public Short value() {
        return this.h();
    }
}
