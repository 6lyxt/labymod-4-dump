// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.program.parameters.ShaderMaterialRenderParameter;
import net.labymod.api.client.gfx.pipeline.program.parameters.ShaderProgramRenderParameter;
import java.util.List;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.DrawingMode;

public class PostProcessRenderProgram extends RenderProgram
{
    public PostProcessRenderProgram(final String name, final DrawingMode mode, final VertexFormat vertexFormat) {
        super(name, mode, vertexFormat);
    }
    
    public PostProcessRenderProgram(final String name, final DrawingMode mode, final VertexFormat vertexFormat, final List<RenderParameter> parameters) {
        super(name, mode, vertexFormat, parameters);
    }
    
    @Override
    public void onPostInit() {
        this.parameters.removeIf(parameter -> parameter instanceof ShaderProgramRenderParameter || parameter instanceof ShaderMaterialRenderParameter);
    }
    
    @Nullable
    @Override
    public ShaderProgram shaderProgram() {
        return null;
    }
}
