// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.StringSplitterAccessor;

@Mixin({ fra.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    fra.f a;
    
    @Override
    public fra.f getWidthProvider() {
        return this.a;
    }
}
