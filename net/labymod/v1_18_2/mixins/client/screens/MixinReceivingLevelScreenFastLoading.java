// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.screens;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ edv.class })
public class MixinReceivingLevelScreenFastLoading
{
    @Shadow
    private boolean n;
    
    @Inject(method = { "loadingPacketsReceived" }, at = { @At("HEAD") })
    private void labyMod$fastLoading(final CallbackInfo ci) {
        this.n = true;
    }
}
