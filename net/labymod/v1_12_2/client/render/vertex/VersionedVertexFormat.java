// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.vertex;

import com.google.common.collect.UnmodifiableIterator;
import net.labymod.api.client.render.shader.ShaderProgram;
import java.util.HashSet;
import java.util.Collection;
import net.labymod.api.client.render.vertex.OldVertexFormat;

public class VersionedVertexFormat implements OldVertexFormat
{
    private final cea vertexFormat;
    private final Collection<String> attributeNames;
    
    public VersionedVertexFormat(final cea vertexFormat) {
        this.vertexFormat = vertexFormat;
        this.attributeNames = new HashSet<String>();
    }
    
    @Override
    public void setupAttributeLocation(final ShaderProgram program) {
        final CustomVertexFormat cvf = (CustomVertexFormat)this.vertexFormat;
        int location = 0;
        for (final String attributeName : cvf.getAttributeNames()) {
            program.bindAttributeLocation(location, attributeName);
            ++location;
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
