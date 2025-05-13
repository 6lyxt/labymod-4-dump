// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.nbt.tags.NBTTagString;

@Mixin({ ea.class })
public abstract class MixinNBTTagString implements NBTTagString
{
    @Shadow
    public abstract String a_();
    
    @Nullable
    @Override
    public String value() {
        return this.a_();
    }
}
