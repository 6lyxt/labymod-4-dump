// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.mojang.blaze3d.vertex;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Objects;
import net.labymod.core.client.render.shader.DefaultShaderProgramApplier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fad.class })
public class MixinVertexBuffer
{
    private static final DefaultShaderProgramApplier PROGRAM_APPLIER;
    
    @Redirect(method = { "_drawWithShader" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShaderInstance;apply()V"))
    private void labyMod$applyShaderInstance(final gee shaderInstance) {
        final DefaultShaderProgramApplier program_APPLIER = MixinVertexBuffer.PROGRAM_APPLIER;
        Objects.requireNonNull(shaderInstance);
        program_APPLIER.apply(shaderInstance::g);
    }
    
    @Redirect(method = { "_drawWithShader" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShaderInstance;clear()V"))
    private void labyMod$clearShaderInstance(final gee shaderInstance) {
        final DefaultShaderProgramApplier program_APPLIER = MixinVertexBuffer.PROGRAM_APPLIER;
        Objects.requireNonNull(shaderInstance);
        program_APPLIER.stop(shaderInstance::f);
    }
    
    static {
        PROGRAM_APPLIER = new DefaultShaderProgramApplier();
    }
}
