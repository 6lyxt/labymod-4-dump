// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_4.client.StringSplitterAccessor;

@Mixin({ evu.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    evu.f a;
    
    @Override
    public evu.f getWidthProvider() {
        return this.a;
    }
}
