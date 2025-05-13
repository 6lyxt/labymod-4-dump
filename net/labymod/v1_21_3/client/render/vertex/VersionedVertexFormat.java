// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.render.vertex;

import net.labymod.api.client.render.shader.ShaderProgram;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashSet;
import java.util.Collection;
import net.labymod.api.client.render.vertex.OldVertexFormat;

public class VersionedVertexFormat implements OldVertexFormat
{
    private final fgx vertexFormat;
    private final gmd shaderProgram;
    private final Collection<String> attributeNames;
    
    public VersionedVertexFormat(final fgx vertexFormat, final gmd shaderProgram) {
        this.vertexFormat = vertexFormat;
        this.shaderProgram = shaderProgram;
        (this.attributeNames = new HashSet<String>()).addAll(this.vertexFormat.d());
    }
    
    public void applyShader() {
        RenderSystem.setShader(this.shaderProgram);
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
}
