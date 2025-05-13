// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.shader;

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
@Implements(value = ShaderInstance.class, key = "frame_buffer_object_shader_instance")
public class FrameBufferObjectShaderInstance extends ShaderInstance
{
    @Inject
    public FrameBufferObjectShaderInstance(final ShaderProvider shaderProvider) {
        super(shaderProvider);
    }
    
    @Override
    protected void internalPrepare(final OldVertexFormat format) throws ShaderException {
        (this.shaderProgram = this.shaderProvider.createProgram(format).addFragmentShader(this.shader(true)).addVertexShader(this.shader(false))).link();
        this.shaderProgram.addUniform(new UniformSampler("FBOColor0", 0));
        this.shaderProgram.addUniform(new UniformSampler("FBODepth0", 1));
        this.shaderProgram.addUniform(new UniformSampler("FBOColor1", 2));
        this.shaderProgram.addUniform(new UniformSampler("FBODepth1", 3));
    }
    
    private ResourceLocation shader(final boolean fragment) {
        return ResourceLocation.create("labymod", "shaders/core/fbo." + (fragment ? "fsh" : "vsh"));
    }
}
