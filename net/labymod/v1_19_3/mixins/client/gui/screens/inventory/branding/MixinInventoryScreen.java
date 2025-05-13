// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.gui.screens.inventory.branding;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eqx.class })
public class MixinInventoryScreen
{
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderTooltip(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", shift = At.Shift.BEFORE) })
    private void labyMod$renderBranding(final eed poseStack, final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        BrandingRenderer.renderCentered(((VanillaStackAccessor)poseStack).stack(), 96.0f);
    }
}
