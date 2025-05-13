// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards.texture;

import net.labymod.api.client.render.vertex.shard.RenderShard;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Optional;

public class TexturingRenderShard extends EmptyTextureRenderShard
{
    private final Optional<ResourceLocation> texture;
    private final boolean blur;
    private final boolean mipmap;
    
    public TexturingRenderShard(final ResourceLocation texture, final boolean blur, final boolean mipmap) {
        super(shard -> {
            shard.gfx().setActiveTexture(0);
            shard.gfx().enableTexture();
            shard.gfx().bindResourceLocation(texture, blur, mipmap);
            return;
        }, shard -> shard.gfx().bindTexture2D(TextureId.ZERO));
        this.texture = Optional.of(texture);
        this.blur = blur;
        this.mipmap = mipmap;
    }
    
    public boolean isBlur() {
        return this.blur;
    }
    
    public boolean isMipmap() {
        return this.mipmap;
    }
    
    @Override
    public Optional<ResourceLocation> texture() {
        return this.texture;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TexturingRenderShard that = (TexturingRenderShard)o;
        return this.blur == that.blur && this.mipmap == that.mipmap && Objects.equals(this.texture, that.texture);
    }
    
    @Override
    public int hashCode() {
        int result = this.texture.hashCode();
        result = 31 * result + (this.blur ? 1 : 0);
        result = 31 * result + (this.mipmap ? 1 : 0);
        return result;
    }
}
