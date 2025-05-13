// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_1.client.StringSplitterAccessor;

@Mixin({ enz.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    enz.f a;
    
    @Override
    public enz.f getWidthProvider() {
        return this.a;
    }
}
