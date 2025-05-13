// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.shader;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.shader.ShaderException;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import javax.inject.Inject;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.shader.program.ShaderInstance;

@Singleton
@Implements(value = ShaderInstance.class, key = "frame_buffer_object_stencil_shader_instance")
public class FrameBufferObjectStencilShaderInstance extends ShaderInstance
{
    @Inject
    public FrameBufferObjectStencilShaderInstance(final ShaderProvider shaderProvider) {
        super(shaderProvider);
    }
    
    @Override
    protected void internalPrepare(final OldVertexFormat format) throws ShaderException {
        (this.shaderProgram = this.shaderProvider.createProgram(format).addFragmentShader(this.shaderSource(true)).addFragmentShader(this.shaderSource(false))).link();
    }
    
    private ResourceLocation shaderSource(final boolean fragment) {
        return ResourceLocation.create("labymod", "shaders/core/fbo_stencil." + (fragment ? "fsh" : "vsh"));
    }
}
