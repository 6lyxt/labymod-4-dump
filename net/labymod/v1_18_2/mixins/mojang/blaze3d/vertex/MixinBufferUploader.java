// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.mojang.blaze3d.vertex;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Objects;
import net.labymod.core.client.render.shader.DefaultShaderProgramApplier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dti.class })
public abstract class MixinBufferUploader
{
    private static final DefaultShaderProgramApplier PROGRAM_APPLIER;
    
    @Redirect(method = { "_end" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShaderInstance;apply()V"))
    private static void labyMod$applyShaderInstance(final erd shaderInstance) {
        final DefaultShaderProgramApplier program_APPLIER = MixinBufferUploader.PROGRAM_APPLIER;
        Objects.requireNonNull(shaderInstance);
        program_APPLIER.apply(shaderInstance::g);
    }
    
    @Redirect(method = { "_end" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShaderInstance;clear()V"))
    private static void labyMod$clearShaderInstance(final erd shaderInstance) {
        final DefaultShaderProgramApplier program_APPLIER = MixinBufferUploader.PROGRAM_APPLIER;
        Objects.requireNonNull(shaderInstance);
        program_APPLIER.stop(shaderInstance::f);
    }
    
    static {
        PROGRAM_APPLIER = new DefaultShaderProgramApplier();
    }
}
