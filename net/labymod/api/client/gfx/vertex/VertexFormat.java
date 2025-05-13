// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.vertex.attribute.VertexAttribute;
import java.util.Collection;

public interface VertexFormat
{
    void apply();
    
    void clear();
    
    int getStride();
    
    default int getIntegerStride() {
        return this.getStride() / 4;
    }
    
    Collection<String> getAttributeNames();
    
    VertexAttribute[] getAttributes();
    
    ShaderProgram getShader();
    
    RenderedBuffer getImmediateDrawBuffer();
    
    @Referenceable
    public interface Builder
    {
        Builder addAttribute(final String p0, final VertexAttribute p1);
        
        Builder addShaderProgram(final ShaderProgram p0);
        
        VertexFormat build();
    }
}
