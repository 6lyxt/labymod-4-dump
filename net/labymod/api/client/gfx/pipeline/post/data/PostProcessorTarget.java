// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.target.RenderTarget;

public interface PostProcessorTarget
{
    String getName();
    
    Filter filter();
    
    RenderTarget create();
    
    default TextureFilter textureFilter() {
        return switch (this.filter().ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> TextureFilter.LINEAR;
            case 1 -> TextureFilter.NEAREST;
        };
    }
    
    public enum Filter
    {
        LINEAR, 
        NEAREST;
    }
    
    public static class FullSizeTarget implements PostProcessorTarget
    {
        @SerializedName("name")
        private String name;
        @SerializedName("filter")
        private Filter filter;
        
        public FullSizeTarget(final String name) {
            this.filter = Filter.NEAREST;
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public Filter filter() {
            return this.filter;
        }
        
        @Override
        public RenderTarget create() {
            final RenderTarget renderTarget = new RenderTarget();
            renderTarget.addColorAttachment(0, this.textureFilter());
            renderTarget.createTarget();
            return renderTarget;
        }
    }
    
    public static class CustomSizeTarget implements PostProcessorTarget
    {
        @SerializedName("name")
        private String name;
        @SerializedName("filter")
        private Filter filter;
        @SerializedName("width")
        private int width;
        @SerializedName("height")
        private int height;
        
        public CustomSizeTarget(final String name, final int width, final int height) {
            this.filter = Filter.NEAREST;
            this.name = name;
            this.width = width;
            this.height = height;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public Filter filter() {
            return this.filter;
        }
        
        @Override
        public RenderTarget create() {
            final RenderTarget renderTarget = new RenderTarget();
            renderTarget.addColorAttachment(0, this.textureFilter());
            renderTarget.createTarget(this.width, this.height);
            return renderTarget;
        }
    }
}
