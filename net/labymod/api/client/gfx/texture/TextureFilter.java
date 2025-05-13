// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.texture;

public enum TextureFilter
{
    NEAREST(9728), 
    LINEAR(9729), 
    NEAREST_MIPMAP_NEAREST(9984), 
    LINEAR_MIPMAP_NEAREST(9985), 
    NEAREST_MIPMAP_LINEAR(9986), 
    LINEAR_MIPMAP_LINEAR(9987);
    
    private final int id;
    
    private TextureFilter(final int id) {
        this.id = id;
    }
    
    @Deprecated(forRemoval = true, since = "4.2.41")
    public static TextureFilter from(final GFXTextureFilter filter) {
        return switch (filter) {
            default -> throw new MatchException(null, null);
            case NEAREST -> TextureFilter.NEAREST;
            case LINEAR -> TextureFilter.LINEAR;
            case NEAREST_MIPMAP_NEAREST -> TextureFilter.NEAREST_MIPMAP_NEAREST;
            case LINEAR_MIPMAP_NEAREST -> TextureFilter.LINEAR_MIPMAP_NEAREST;
            case NEAREST_MIPMAP_LINEAR -> TextureFilter.NEAREST_MIPMAP_LINEAR;
            case LINEAR_MIPMAP_LINEAR -> TextureFilter.LINEAR_MIPMAP_LINEAR;
        };
    }
    
    public int getId() {
        return this.id;
    }
}
