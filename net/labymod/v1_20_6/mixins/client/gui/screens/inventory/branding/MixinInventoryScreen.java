// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.gui.screens.inventory.branding;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.gfx.pipeline.renderer.util.BrandingRenderer;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fpe.class })
public class MixinInventoryScreen
{
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V", shift = At.Shift.BEFORE) })
    private void labyMod$renderBranding(final fgt graphics, final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        BrandingRenderer.renderCentered(((VanillaStackAccessor)graphics.c()).stack(), 96.0f);
    }
}
