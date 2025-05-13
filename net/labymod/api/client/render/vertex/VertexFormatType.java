// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex;

import java.util.Locale;

public enum VertexFormatType
{
    POSITION_COLOR, 
    POSITION_TEXTURE_COLOR, 
    POSITION_COLOR_NORMAL, 
    ENTITY, 
    POSITION_COLOR_TEXTURE_LIGHTMAP;
    
    private final String vertexName;
    
    private VertexFormatType() {
        this.vertexName = this.name().toLowerCase(Locale.ENGLISH);
    }
    
    public String vertexName() {
        return this.vertexName;
    }
}
