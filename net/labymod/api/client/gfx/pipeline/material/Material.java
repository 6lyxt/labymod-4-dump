// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.material;

import org.jetbrains.annotations.NotNull;
import java.util.function.Function;
import net.labymod.api.Textures;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;

public interface Material
{
    default Builder builder() {
        return new Builder();
    }
    
    ResourceLocation getTextureLocation();
    
    MaterialColor getColor();
    
    boolean isGlowing();
    
    public static class Builder
    {
        @Nullable
        private ResourceLocation textureLocation;
        private MaterialColor color;
        private boolean glowing;
        
        public Builder() {
            this.textureLocation = Textures.EMPTY;
            this.color = new MaterialColor();
        }
        
        public Builder withTextureLocation(final ResourceLocation location) {
            this.textureLocation = location;
            return this;
        }
        
        public Builder withColor(final MaterialColor color) {
            this.color = color;
            return this;
        }
        
        public Builder withGlow() {
            this.glowing = true;
            return this;
        }
        
        @NotNull
        public Material build(@NotNull final Function<Builder, Material> builderFunction) {
            return builderFunction.apply(this);
        }
        
        public ResourceLocation getTextureLocation() {
            return this.textureLocation;
        }
        
        public MaterialColor getColor() {
            return this.color;
        }
        
        public boolean isGlowing() {
            return this.glowing;
        }
    }
}
