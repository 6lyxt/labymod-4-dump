// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gfx.pipeline.blaze3d.program;

import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;
import java.util.List;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;

public class VersionedRenderProgram extends RenderProgram
{
    private final bmu vertexFormat;
    
    public VersionedRenderProgram(final String name, final DrawingMode mode, final bmu vertexFormat) {
        super(name, mode, null);
        this.vertexFormat = vertexFormat;
    }
    
    public VersionedRenderProgram(final String name, final DrawingMode mode, final bmu vertexFormat, final List<RenderParameter> parameters) {
        super(name, mode, null, parameters);
        this.vertexFormat = vertexFormat;
    }
    
    @Override
    public VertexFormat vertexFormat() {
        return null;
    }
    
    @Override
    public ShaderProgram shaderProgram() {
        return null;
    }
    
    public bmu getVertexFormat() {
        return this.vertexFormat;
    }
}
