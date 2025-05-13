// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_5.client.StringSplitterAccessor;

@Mixin({ ffr.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    ffr.f a;
    
    @Override
    public ffr.f getWidthProvider() {
        return this.a;
    }
}
