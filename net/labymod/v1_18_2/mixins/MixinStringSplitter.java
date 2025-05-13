// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_18_2.client.StringSplitterAccessor;

@Mixin({ dze.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    dze.f a;
    
    @Override
    public dze.f getWidthProvider() {
        return this.a;
    }
}
