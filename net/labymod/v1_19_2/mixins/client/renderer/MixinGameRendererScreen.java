// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.Laby;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ezl.class })
public class MixinGameRendererScreen
{
    private final DefaultRenderContexts renderContexts;
    
    public MixinGameRendererScreen() {
        this.renderContexts = (DefaultRenderContexts)Laby.references().renderContexts();
    }
    
    @WrapOperation(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V") })
    private void labyMod$storeStack(final elm instance, final eaq poseStack, final int mouseX, final int mouseY, final float tickDelta, final Operation<Void> original) {
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(instance);
        this.renderContexts.setCurrentStack(stack);
        ScreenUtil.wrapRender(stack, tickDelta, () -> original.call(new Object[] { instance, poseStack, mouseX, mouseY, tickDelta }));
        this.renderContexts.setCurrentStack(null);
        screenCustomFontStack.pop(instance);
    }
}
