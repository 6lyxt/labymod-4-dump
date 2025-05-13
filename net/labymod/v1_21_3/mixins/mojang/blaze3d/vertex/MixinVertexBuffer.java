// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.mojang.blaze3d.vertex;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.core.client.render.shader.DefaultShaderProgramApplier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fgv.class })
public class MixinVertexBuffer
{
    private static final DefaultShaderProgramApplier PROGRAM_APPLIER;
    
    @WrapOperation(method = { "drawWithShader" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CompiledShaderProgram;apply()V") })
    private void labyMod$applyShaderInstance(final gku compiledShader, final Operation<Void> original) {
        MixinVertexBuffer.PROGRAM_APPLIER.apply(() -> original.call(new Object[] { compiledShader }));
    }
    
    @WrapOperation(method = { "drawWithShader" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CompiledShaderProgram;clear()V") })
    private void labyMod$clearShaderInstance(final gku compiledShader, final Operation<Void> original) {
        MixinVertexBuffer.PROGRAM_APPLIER.stop(() -> original.call(new Object[] { compiledShader }));
    }
    
    static {
        PROGRAM_APPLIER = new DefaultShaderProgramApplier();
    }
}
