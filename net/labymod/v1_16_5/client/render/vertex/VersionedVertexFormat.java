// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.render.vertex;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import net.labymod.api.client.render.shader.ShaderProgram;
import java.util.HashSet;
import java.util.Collection;
import net.labymod.api.client.render.vertex.OldVertexFormat;

public class VersionedVertexFormat implements OldVertexFormat
{
    private final dfr vertexFormat;
    private final Collection<String> attributeNames;
    
    public VersionedVertexFormat(final dfr vertexFormat) {
        this.vertexFormat = vertexFormat;
        this.attributeNames = new HashSet<String>();
    }
    
    public void applyShader() {
    }
    
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        int attributeLocation = 0;
        final VertexFormatAccessor formatAccessor = (VertexFormatAccessor)this.vertexFormat;
        for (final Map.Entry<String, dfs> entry : formatAccessor.getElements().entrySet()) {
            program.bindAttributeLocation(attributeLocation, entry.getKey());
            ++attributeLocation;
        }
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
