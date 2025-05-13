// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.cosmetics;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.renderer.GlowAccessor;

@Mixin({ bzf.class })
public class MixinRenderManagerGlowing implements GlowAccessor
{
    @Shadow
    private boolean r;
    
    @Override
    public boolean isGlowing() {
        return this.r;
    }
}
