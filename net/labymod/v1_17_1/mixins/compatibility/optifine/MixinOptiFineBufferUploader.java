// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Objects;
import net.labymod.core.client.render.shader.DefaultShaderProgramApplier;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_17_1.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ dqh.class })
public abstract class MixinOptiFineBufferUploader
{
    private static final DefaultShaderProgramApplier PROGRAM_APPLIER;
    
    @Redirect(method = { "draw(Ljava/nio/ByteBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;Lcom/mojang/blaze3d/vertex/VertexFormat;ILcom/mojang/blaze3d/vertex/VertexFormat$IndexType;IZLnet/optifine/render/MultiTextureData;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShaderInstance;apply()V"))
    @Dynamic
    private static void labyMod$applyShaderInstance(final ent shaderInstance) {
        final DefaultShaderProgramApplier program_APPLIER = MixinOptiFineBufferUploader.PROGRAM_APPLIER;
        Objects.requireNonNull(shaderInstance);
        program_APPLIER.apply(shaderInstance::g);
    }
    
    @Redirect(method = { "draw(Ljava/nio/ByteBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;Lcom/mojang/blaze3d/vertex/VertexFormat;ILcom/mojang/blaze3d/vertex/VertexFormat$IndexType;IZLnet/optifine/render/MultiTextureData;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShaderInstance;clear()V"), expect = 0)
    @Dynamic
    private static void labyMod$clearShaderInstance(final ent shaderInstance) {
        final DefaultShaderProgramApplier program_APPLIER = MixinOptiFineBufferUploader.PROGRAM_APPLIER;
        Objects.requireNonNull(shaderInstance);
        program_APPLIER.stop(shaderInstance::f);
    }
    
    static {
        PROGRAM_APPLIER = new DefaultShaderProgramApplier();
    }
}
