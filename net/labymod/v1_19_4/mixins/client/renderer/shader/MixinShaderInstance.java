// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.renderer.shader;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_19_4.client.renderer.ShaderInstanceExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fir.class })
public abstract class MixinShaderInstance
{
    private final ShaderInstanceExtension labyMod$shaderInstanceExtension;
    
    public MixinShaderInstance() {
        this.labyMod$shaderInstanceExtension = new ShaderInstanceExtension();
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/server/packs/resources/ResourceProvider;Ljava/lang/String;Lcom/mojang/blaze3d/vertex/VertexFormat;)V" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/shaders/ProgramManager;linkShader(Lcom/mojang/blaze3d/shaders/Shader;)V"), require = 0, expect = 0)
    private void labyMod$registerCustomUniform(final egu shader) {
        this.labyMod$shaderInstanceExtension.registerCustomUniform(shader, (fir)this);
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/server/packs/resources/ResourceProvider;Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/blaze3d/vertex/VertexFormat;)V" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/shaders/ProgramManager;linkShader(Lcom/mojang/blaze3d/shaders/Shader;)V"), require = 0, expect = 0)
    @Dynamic
    private void labyMod$optifine$registerCustomUniform(final egu shader) {
        this.labyMod$shaderInstanceExtension.registerCustomUniform(shader, (fir)this);
    }
    
    @Insert(method = { "apply" }, at = @At("TAIL"))
    private void labyMod$applyCustomUniform(final InsertInfo info) {
        this.labyMod$shaderInstanceExtension.applyCustomUniforms();
    }
}
