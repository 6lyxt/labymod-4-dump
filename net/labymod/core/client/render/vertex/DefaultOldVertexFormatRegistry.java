// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.vertex;

import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(OldVertexFormatRegistry.class)
public class DefaultOldVertexFormatRegistry extends DefaultRegistry<OldVertexFormat> implements OldVertexFormatRegistry
{
    private OldVertexFormat positionColor;
    private OldVertexFormat positionTextureColor;
    private OldVertexFormat positionColorTextureLightmap;
    private OldVertexFormat entity;
    private OldVertexFormat positionColorNormal;
    
    @Inject
    public DefaultOldVertexFormatRegistry() {
    }
    
    @Override
    public OldVertexFormat getPositionColor() {
        if (this.positionColor == null) {
            this.positionColor = this.getById("position_color");
        }
        return this.positionColor;
    }
    
    @Override
    public OldVertexFormat getPositionTextureColor() {
        if (this.positionTextureColor == null) {
            this.positionTextureColor = this.getById("position_texture_color");
        }
        return this.positionTextureColor;
    }
    
    @Override
    public OldVertexFormat getPositionColorNormal() {
        if (this.positionColorNormal == null) {
            this.positionColorNormal = this.getById("position_color_normal");
        }
        return this.positionColorNormal;
    }
    
    @Override
    public OldVertexFormat getPositionColorTextureLightmap() {
        if (this.positionColorTextureLightmap == null) {
            this.positionColorTextureLightmap = this.getById("position_color_texture_lightmap");
        }
        return this.positionColorTextureLightmap;
    }
    
    @Override
    public OldVertexFormat getEntity() {
        if (this.entity == null) {
            this.entity = this.getById("entity");
        }
        return this.entity;
    }
}
