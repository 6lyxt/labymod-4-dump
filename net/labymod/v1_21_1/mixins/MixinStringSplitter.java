// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_1.client.StringSplitterAccessor;

@Mixin({ fgz.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    fgz.f a;
    
    @Override
    public fgz.f getWidthProvider() {
        return this.a;
    }
}
