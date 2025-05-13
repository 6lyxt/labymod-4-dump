// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_6.client.StringSplitterAccessor;

@Mixin({ ffs.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    ffs.f a;
    
    @Override
    public ffs.f getWidthProvider() {
        return this.a;
    }
}
