// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.client.StringSplitterAccessor;

@Mixin({ dkj.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    dkj.f a;
    
    @Override
    public dkj.f getWidthProvider() {
        return this.a;
    }
}
