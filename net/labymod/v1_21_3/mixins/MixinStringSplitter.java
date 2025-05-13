// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.client.StringSplitterAccessor;

@Mixin({ fmq.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    fmq.f a;
    
    @Override
    public fmq.f getWidthProvider() {
        return this.a;
    }
}
