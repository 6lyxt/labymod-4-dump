// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

public enum AttributeMask
{
    CURRENT_BIT(1), 
    POINT_BIT(2), 
    LINE_BIT(4), 
    POLYGON_BIT(8), 
    POLYGON_STIPPLE_BIT(16), 
    PIXEL_MODE_BIT(32), 
    LIGHTING_BIT(64), 
    FOG_BIT(128), 
    DEPTH_BUFFER_BIT(256), 
    ACCUM_BUFFER_BIT(512), 
    STENCIL_BUFFER_BIT(1024), 
    VIEWPORT_BIT(2048), 
    TRANSFORM_BIT(4096), 
    ENABLE_BIT(8192), 
    COLOR_BUFFER_BIT(16384), 
    HINT_BIT(32768), 
    EVAL_BIT(65536), 
    LIST_BIT(131072), 
    TEXTURE_BIT(262144), 
    SCISSOR_BIT(524288), 
    ALL_ATTRIBUTE_BITS(1048575), 
    COLOR_DEPTH_BUFFER_BIT(16640), 
    COLOR_DEPTH_STENCIL_BUFFER_BIT(17664);
    
    private final int id;
    
    private AttributeMask(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
