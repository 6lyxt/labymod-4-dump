// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_2.client.StringSplitterAccessor;

@Mixin({ erh.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    erh.f a;
    
    @Override
    public erh.f getWidthProvider() {
        return this.a;
    }
}
