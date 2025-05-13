// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_4.client.StringSplitterAccessor;

@Mixin({ emt.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    emt.f a;
    
    @Override
    public emt.f getWidthProvider() {
        return this.a;
    }
}
