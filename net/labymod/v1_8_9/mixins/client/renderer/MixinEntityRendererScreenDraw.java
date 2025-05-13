// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_8_9.client.gfx.pipeline.util.VersionedScreenUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfk.class })
public class MixinEntityRendererScreenDraw
{
    @Redirect(method = { "updateCameraAndRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(IIF)V"))
    private void labyMod$fireScreenRenderEvent(final axu screen, final int mouseX, final int mouseY, final float tickDelta) {
        VersionedScreenUtil.drawScreen(screen, mouseX, mouseY, tickDelta);
    }
}
