// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_2.client.StringSplitterAccessor;

@Mixin({ egh.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    egh.h a;
    
    @Override
    public egh.h getWidthProvider() {
        return this.a;
    }
}
