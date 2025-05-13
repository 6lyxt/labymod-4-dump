// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.mojang.blaze3d.opengl;

import net.labymod.api.util.RenderUtil;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fjd.class })
public class MixinGlCommandEncoder_StencilFramebuffer
{
    @WrapOperation(method = { "createRenderPass(Lcom/mojang/blaze3d/textures/GpuTexture;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTexture;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;", "finishRenderPass" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_glBindFramebuffer(II)V") })
    private void labyMod$disableFramebufferBinding(final int $$0, final int $$1, final Operation<Void> original) {
        if (!Laby.references().gfxRenderPipeline().isActivityRenderTarget()) {
            original.call(new Object[] { $$0, $$1 });
        }
    }
    
    @WrapOperation(method = { "applyPipelineState" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_colorMask(ZZZZ)V") })
    private void labyMod$disableColorMaskInStencilPass(final boolean $$0, final boolean $$1, final boolean $$2, final boolean $$3, final Operation<Void> original) {
        if (!RenderUtil.isStencilPass()) {
            original.call(new Object[] { $$0, $$1, $$2, $$3 });
        }
    }
}
