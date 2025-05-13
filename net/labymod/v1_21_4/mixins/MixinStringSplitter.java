// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.client.StringSplitterAccessor;

@Mixin({ flu.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    flu.f a;
    
    @Override
    public flu.f getWidthProvider() {
        return this.a;
    }
}
