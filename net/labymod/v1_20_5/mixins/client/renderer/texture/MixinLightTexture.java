// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.renderer.texture;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.gfx.texture.overlay.DynamicOverlayTexture;
import net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gdo.class })
public class MixinLightTexture
{
    @Inject(method = { "updateLightTexture" }, at = { @At("HEAD") })
    private void labyMod$updateOverlayTexture(final float partialTicks, final CallbackInfo ci) {
        final DynamicOverlayTexture texture = ((GameOverlayTexture)ffg.Q().j.n()).dynamicTexture();
        texture.setColorAndUpdate();
    }
}
