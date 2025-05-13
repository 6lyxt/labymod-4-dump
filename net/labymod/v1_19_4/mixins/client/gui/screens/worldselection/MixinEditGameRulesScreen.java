// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.gui.screens.worldselection;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ exo.class })
public abstract class MixinEditGameRulesScreen extends etd
{
    protected MixinEditGameRulesScreen(final tj param0) {
        super(param0);
    }
    
    @Inject(method = { "render" }, at = { @At("HEAD") })
    public void labyMod$renderBackground(final ehe param0, final int param1, final int param2, final float param3, final CallbackInfo ci) {
        this.a(param0);
    }
}
