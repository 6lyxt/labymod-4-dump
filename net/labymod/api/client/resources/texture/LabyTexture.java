// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.GFXBridge;

public abstract class LabyTexture implements Texture, AutoCloseable
{
    private static final GFXBridge GFX;
    private static final int NOT_ASSIGNED = -1;
    private final TextureTarget target;
    private final ResourceLocation textureLocation;
    private int id;
    private boolean blur;
    private boolean mipmap;
    
    protected LabyTexture(final ResourceLocation textureLocation) {
        this.id = -1;
        this.blur = false;
        this.mipmap = false;
        this.textureLocation = textureLocation;
        this.target = TextureTarget.TEXTURE_2D;
    }
    
    public void load() {
    }
    
    public void bind() {
        LabyTexture.GFX.bindTexture(this.getTarget(), TextureId.of(this.getTextureId()));
    }
    
    public void setTextureFiltering(final boolean blur, final boolean mipmap) {
        this.blur = blur;
        this.mipmap = mipmap;
        TextureFilter minFilter;
        TextureFilter magFilter;
        if (blur) {
            minFilter = (mipmap ? TextureFilter.LINEAR_MIPMAP_LINEAR : TextureFilter.LINEAR);
            magFilter = TextureFilter.LINEAR;
        }
        else {
            minFilter = (mipmap ? TextureFilter.NEAREST_MIPMAP_LINEAR : TextureFilter.NEAREST);
            magFilter = TextureFilter.NEAREST;
        }
        this.bind();
        LabyTexture.GFX.texParameter(this.getTarget(), TextureParameterName.TEXTURE_MIN_FILTER, minFilter);
        LabyTexture.GFX.texParameter(this.getTarget(), TextureParameterName.TEXTURE_MAG_FILTER, magFilter);
    }
    
    @Override
    public int getTextureId() {
        if (!this.isAssigned()) {
            this.id = LabyTexture.GFX.genTextures();
        }
        return this.id;
    }
    
    public void release() {
        if (this.isAssigned()) {
            LabyTexture.GFX.deleteTextures(this.id);
            this.id = -1;
        }
    }
    
    @Override
    public void close() {
    }
    
    public boolean isBlur() {
        return this.blur;
    }
    
    public boolean isMipmap() {
        return this.mipmap;
    }
    
    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }
    
    protected TextureTarget getTarget() {
        return this.target;
    }
    
    private boolean isAssigned() {
        return this.id != -1;
    }
    
    static {
        GFX = Laby.gfx();
    }
}
