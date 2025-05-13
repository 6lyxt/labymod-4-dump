// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.material;

import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.shader.UniformContext;
import net.labymod.api.client.gfx.pipeline.Fog;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import java.util.function.Function;
import net.labymod.api.client.gfx.shader.uniform.UniformDynamicSampler;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.client.resources.ResourceLocation;

public final class ShaderMaterials
{
    public static final ResourceLocation SPRAY_VERTEX_SHADER_LOCATION;
    public static final ResourceLocation SPRAY_FRAGMENT_SHADER_LOCATION;
    public static final ShaderMaterial SPRAY_SHADER_MATERIAL;
    
    private static ShaderMaterial buildSprayShaderMaterial() {
        return ShaderMaterial.builder(context -> {
            final UniformMatrix4 projectionMatrix = context.addUniform(new UniformMatrix4("ProjectionMatrix"));
            final UniformMatrix4 modelViewMatrix = context.addUniform(new UniformMatrix4("ModelViewMatrix"));
            final Uniform1F fogStart = context.addUniform(new Uniform1F("FogStart"));
            final Uniform1F fogEnd = context.addUniform(new Uniform1F("FogEnd"));
            context.addUniform(new Uniform2I("LightCoords"));
            context.addUniform(new Uniform1F("Wear"));
            context.addUniform(new UniformSampler("DiffuseSampler", 0));
            context.addUniform(UniformDynamicSampler.LIGHTMAP_SAMPLER.apply("LightSampler", 1));
            context.addUniform(new UniformSampler("WearSampler", 4));
            context.setUniformApplier(applier -> {
                projectionMatrix.set(applier.getProjectionMatrix());
                modelViewMatrix.set(applier.getModelViewMatrix());
                final Fog fog = applier.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().fog();
                fogStart.set(fog.getSuppliedStart());
                fogEnd.set(fog.getSuppliedEnd());
            });
        }).addVertexShader(ShaderMaterials.SPRAY_VERTEX_SHADER_LOCATION).addFragmentShader(ShaderMaterials.SPRAY_FRAGMENT_SHADER_LOCATION).withDebugName("Spray").build((Function<ShaderProgram, ShaderMaterial>)SprayShaderMaterial::new);
    }
    
    static {
        SPRAY_VERTEX_SHADER_LOCATION = ResourceLocation.create("labymod", "shaders/vertex/spray.vsh");
        SPRAY_FRAGMENT_SHADER_LOCATION = ResourceLocation.create("labymod", "shaders/vertex/spray.fsh");
        SPRAY_SHADER_MATERIAL = buildSprayShaderMaterial();
    }
}
