// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.renderer.state;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.client.renderer.ItemStackRenderStateAccessor;

@Mixin({ hbp.class })
public class MixinItemStackRenderState implements ItemStackRenderStateAccessor
{
    @Shadow
    private boolean b;
    @Shadow
    private cwo a;
    
    @Override
    public boolean labyMod$isLeftHand() {
        return this.b;
    }
    
    @Override
    public cwo labyMod$getItemDisplayContext() {
        return this.a;
    }
}
