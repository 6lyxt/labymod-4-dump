// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.StringSplitterAccessor;

@Mixin({ ejs.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    ejs.f a;
    
    @Override
    public ejs.f getWidthProvider() {
        return this.a;
    }
}
