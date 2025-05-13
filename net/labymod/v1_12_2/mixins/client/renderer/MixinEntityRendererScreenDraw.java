// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_12_2.client.gfx.pipeline.util.VersionedScreenUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ buq.class })
public class MixinEntityRendererScreenDraw
{
    @WrapOperation(method = { "updateCameraAndRender" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(IIF)V") })
    private void labyMod$fireScreenRenderEvent(final blk screen, final int mouseX, final int mouseY, final float tickDelta, final Operation<Void> original) {
        VersionedScreenUtil.drawScreen(original, screen, mouseX, mouseY, tickDelta);
    }
}
