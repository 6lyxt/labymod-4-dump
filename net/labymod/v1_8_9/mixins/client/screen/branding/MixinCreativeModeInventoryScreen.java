// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.screen.branding;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ayu.class })
public class MixinCreativeModeInventoryScreen
{
    @Inject(method = { "drawScreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/InventoryEffectRenderer;drawScreen(IIF)V", shift = At.Shift.AFTER) })
    private void labyMod$renderBranding(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        avc.a();
        BrandingRenderer.renderCentered(VersionedStackProvider.DEFAULT_STACK, 108.0f);
        gfx.restoreBlaze3DStates();
    }
}
