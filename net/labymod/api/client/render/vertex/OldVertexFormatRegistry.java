// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

@Referenceable
public interface OldVertexFormatRegistry extends Registry<OldVertexFormat>
{
    OldVertexFormat getPositionColor();
    
    OldVertexFormat getPositionTextureColor();
    
    OldVertexFormat getPositionColorNormal();
    
    OldVertexFormat getPositionColorTextureLightmap();
    
    OldVertexFormat getEntity();
    
    default void register(final VertexFormatType type, final OldVertexFormat format) {
        this.register(type.vertexName(), format);
    }
    
    default OldVertexFormat getVertexFormat(final String name) {
        return this.getById(name);
    }
    
    default OldVertexFormat getVertexFormat(final VertexFormatType type) {
        return this.getVertexFormat(type.vertexName());
    }
}
