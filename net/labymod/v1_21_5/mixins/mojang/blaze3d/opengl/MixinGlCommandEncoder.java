// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.mojang.blaze3d.opengl;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.render.shader.ShaderProgram;
import net.labymod.v1_21_5.client.gfx.pipeline.LegacyGlProgram;
import net.labymod.api.Laby;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.RenderUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.gfx.pipeline.CommandEncoderPipelineExt;

@Mixin({ fjd.class })
public class MixinGlCommandEncoder implements CommandEncoderPipelineExt
{
    @Shadow
    @Nullable
    private RenderPipeline e;
    @Shadow
    @Nullable
    private fjh g;
    
    @Override
    public void invalidatePipeline() {
        this.e = null;
        this.g = null;
    }
    
    @Inject(method = { "trySetup" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/ScissorState;isEnabled()Z", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$checkCustomRenderPass(final fji $$0, final CallbackInfoReturnable<Boolean> cir) {
        if (RenderUtil.isCustomRenderType()) {
            cir.setReturnValue((Object)true);
        }
    }
    
    @WrapOperation(method = { "trySetup" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlRenderPipeline;program()Lcom/mojang/blaze3d/opengl/GlProgram;") })
    private fjh labyMod$switchGlProgram(final fjj instance, final Operation<fjh> original) {
        final ShaderProgram shaderProgram = Laby.references().glStateBridge().shaderProgram();
        return (fjh)((shaderProgram == null) ? original.call(new Object[] { instance }) : LegacyGlProgram.fromShaderProgram(shaderProgram));
    }
}
