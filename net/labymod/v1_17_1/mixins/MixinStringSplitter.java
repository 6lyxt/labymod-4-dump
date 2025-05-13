// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_17_1.client.StringSplitterAccessor;

@Mixin({ dwa.class })
public class MixinStringSplitter implements StringSplitterAccessor
{
    @Final
    @Shadow
    dwa.f a;
    
    @Override
    public dwa.f getWidthProvider() {
        return this.a;
    }
}
