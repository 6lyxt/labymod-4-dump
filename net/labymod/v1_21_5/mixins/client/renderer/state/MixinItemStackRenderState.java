// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.state;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.renderer.ItemStackRenderStateAccessor;

@Mixin({ hhi.class })
public class MixinItemStackRenderState implements ItemStackRenderStateAccessor
{
    @Shadow
    private dai a;
    
    @Override
    public boolean labyMod$isLeftHand() {
        return this.a.d();
    }
    
    @Override
    public dai labyMod$getItemDisplayContext() {
        return this.a;
    }
}
