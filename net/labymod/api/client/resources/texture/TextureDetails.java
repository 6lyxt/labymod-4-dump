// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import java.util.Objects;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;

record TextureDetails(ResourceLocation location, @Nullable ResourceLocation fallbackLocation, String url, RegisterStrategy registerStrategy, @Nullable GameImageProcessor imageProcessor, @Nullable Consumer<Texture> finishHandler, @Nullable Consumer<CompletableResourceLocation> locationHandler) {
    public static Builder builder() {
        return new Builder();
    }
    
    public GameImage processImage(final GameImage source) {
        return (this.imageProcessor == null) ? source : this.imageProcessor.processImage(source);
    }
    
    public void finish(final Texture texture) {
        if (this.finishHandler != null) {
            this.finishHandler.accept(texture);
        }
    }
    
    public void acceptLocation(final CompletableResourceLocation completableLocation) {
        if (this.locationHandler != null) {
            this.locationHandler.accept(completableLocation);
        }
    }
    
    @Nullable
    public ResourceLocation fallbackLocation() {
        return this.fallbackLocation;
    }
    
    @Nullable
    public GameImageProcessor imageProcessor() {
        return this.imageProcessor;
    }
    
    @Nullable
    public Consumer<Texture> finishHandler() {
        return this.finishHandler;
    }
    
    @Nullable
    public Consumer<CompletableResourceLocation> locationHandler() {
        return this.locationHandler;
    }
    
    public enum RegisterStrategy
    {
        REGISTER, 
        REGISTER_AND_RELEASE;
    }
    
    public static class Builder
    {
        private ResourceLocation location;
        private ResourceLocation fallbackLocation;
        private String url;
        private RegisterStrategy registerStrategy;
        private GameImageProcessor imageProcessor;
        private Consumer<Texture> finishHandler;
        private Consumer<CompletableResourceLocation> locationHandler;
        
        public Builder() {
            this.registerStrategy = RegisterStrategy.REGISTER;
        }
        
        public Builder withLocation(final ResourceLocation location) {
            this.location = location;
            return this;
        }
        
        public Builder withFallbackLocation(final ResourceLocation fallbackLocation) {
            this.fallbackLocation = fallbackLocation;
            return this;
        }
        
        public Builder withUrl(final String url) {
            this.url = url;
            return this;
        }
        
        public Builder withRegisterStrategy(final RegisterStrategy strategy) {
            this.registerStrategy = strategy;
            return this;
        }
        
        public Builder withImageProcessor(final GameImageProcessor imageProcessor) {
            this.imageProcessor = imageProcessor;
            return this;
        }
        
        public Builder withFinishHandler(final Consumer<Texture> finishHandler) {
            this.finishHandler = finishHandler;
            return this;
        }
        
        public Builder withLocationHandler(final Consumer<CompletableResourceLocation> locationHandler) {
            this.locationHandler = locationHandler;
            return this;
        }
        
        public TextureDetails build() {
            Objects.requireNonNull(this.location, "location must not be null");
            Objects.requireNonNull(this.url, "url must not be null");
            Objects.requireNonNull(this.registerStrategy, "registerStrategy must not be null");
            return new TextureDetails(this.location, this.fallbackLocation, this.url, this.registerStrategy, this.imageProcessor, this.finishHandler, this.locationHandler);
        }
    }
}
