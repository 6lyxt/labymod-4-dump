// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader.program;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.shader.ShaderException;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import javax.inject.Inject;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.shader.program.ShaderInstance;

@Singleton
@Implements(value = ShaderInstance.class, key = "renderphase_3d_environment_texture")
public class RenderPhase3DEnvironmentTextureShaderInstance extends ShaderInstance
{
    @Inject
    public RenderPhase3DEnvironmentTextureShaderInstance(final ShaderProvider shaderProvider) {
        super(shaderProvider);
    }
    
    public void internalPrepare(final OldVertexFormat format) throws ShaderException {
        (this.shaderProgram = this.shaderProvider.createProgram(format).addFragmentShader(this.shaderLocation(true)).addVertexShader(this.shaderLocation(false))).link();
        this.shaderProgram.addUniform(new UniformSampler("textureSampler", 0));
        this.shaderProgram.addUniform(new UniformSampler("lightSampler", 2));
    }
    
    private ResourceLocation shaderLocation(final boolean fragment) {
        return ResourceLocation.create("labymod", "shaders/core/renderphase_3d_env_texture." + (fragment ? "fsh" : "vsh"));
    }
}
