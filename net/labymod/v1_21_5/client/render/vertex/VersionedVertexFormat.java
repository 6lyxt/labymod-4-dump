// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.render.vertex;

import net.labymod.api.client.render.shader.ShaderProgram;
import java.util.HashSet;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.Collection;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.labymod.api.client.render.vertex.OldVertexFormat;

public class VersionedVertexFormat implements OldVertexFormat
{
    private final VertexFormat vertexFormat;
    private final Collection<String> attributeNames;
    private final RenderPipeline renderPipeline;
    
    public VersionedVertexFormat(final VertexFormat vertexFormat, final RenderPipeline renderPipeline) {
        this.vertexFormat = vertexFormat;
        this.renderPipeline = renderPipeline;
        (this.attributeNames = new HashSet<String>()).addAll(this.vertexFormat.getElementAttributeNames());
    }
    
    public void applyShader() {
    }
    
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        VertexFormatUtil.bindAttributeLocation(this.vertexFormat, program);
    }
    
    @Override
    public Collection<String> getAttributeNames() {
        return this.attributeNames;
    }
    
    @Override
    public <T> T getMojangVertexFormat() {
        return (T)this.vertexFormat;
    }
    
    public RenderPipeline getRenderPipeline() {
        return this.renderPipeline;
    }
}
