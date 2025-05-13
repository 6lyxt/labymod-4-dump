// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import net.labymod.api.metadata.Metadata;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.util.Iterator;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.generated.ReferenceStorage;
import java.io.IOException;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import java.util.ArrayList;
import java.io.InputStream;
import net.labymod.api.client.resources.texture.GameImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.Resources;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.AnimatedResourceLocation;

public class DefaultAnimatedResourceLocation implements AnimatedResourceLocation
{
    protected static final Logging LOGGER;
    protected final List<AnimatedFrameResourceLocation> animatedResourceLocations;
    private final Resources resources;
    private final long delay;
    @Nullable
    private final Runnable completableListener;
    private GameImage spriteImage;
    private boolean prepared;
    private int frameCount;
    private int currentFrame;
    
    public DefaultAnimatedResourceLocation(final String namespace, final String locationPath, final InputStream spriteImageStream, final int ratioWidth, final int ratioHeight, final long delay, @Nullable final Runnable completableListener) {
        this.animatedResourceLocations = new ArrayList<AnimatedFrameResourceLocation>();
        this.resources = Laby.references().resources();
        this.delay = MathHelper.clamp(delay, 1L, 5000L);
        this.completableListener = completableListener;
        final ReferenceStorage references = Laby.references();
        references.asynchronousTextureUploader().prepareAndUploadTexture(() -> {
            GameImage image;
            try {
                image = references.gameImageProvider().getImage(spriteImageStream);
            }
            catch (final IOException exception) {
                image = null;
                DefaultAnimatedResourceLocation.LOGGER.error("The animated texture cannot be created because the image could not be read", exception);
            }
            this.spriteImage = image;
        }, () -> this.prepareResourceLocations(namespace, locationPath, ratioWidth, ratioHeight));
    }
    
    public DefaultAnimatedResourceLocation(final ResourceLocation[] locations, final long delay, @Nullable final Runnable completableListener) {
        this.animatedResourceLocations = new ArrayList<AnimatedFrameResourceLocation>();
        this.resources = Laby.references().resources();
        this.delay = MathHelper.clamp(delay, 1L, 5000L);
        this.completableListener = completableListener;
        this.uploadTextures(locations);
    }
    
    private void prepareResourceLocations(final String namespace, final String locationPath, final int ratioWidth, final int ratioHeight) {
        if (this.spriteImage == null) {
            return;
        }
        final int frameWidth = this.spriteImage.getWidth();
        final int frameHeight = (int)(frameWidth * (ratioHeight / (double)ratioWidth));
        this.frameCount = this.spriteImage.getHeight() / frameHeight;
        this.currentFrame = 0;
        final List<GameImageTexture> textures = new ArrayList<GameImageTexture>();
        Laby.references().asynchronousTextureUploader().prepareAndUploadTexture(() -> {
            for (int frame = 0; frame < this.frameCount; ++frame) {
                final ResourceLocation animatedResourceLocation = this.resources.resourceLocationFactory().create(namespace, locationPath + "_" + frame + ".png");
                final GameImage sprite = this.spriteImage.getSubImage(0, frame * frameHeight, frameWidth, frameHeight);
                textures.add(this.resources.gameImageTextureFactory().create(animatedResourceLocation, sprite));
            }
        }, () -> {
            textures.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final GameImageTexture texture = iterator.next();
                final ResourceLocation location = texture.getTextureResourceLocation();
                this.resources.textureRepository().register(location, texture);
                this.animatedResourceLocations.add(new DefaultAnimatedFrameResourceLocation(location));
            }
            textures.clear();
            this.setPrepared();
        });
    }
    
    @Override
    public AnimatedFrameResourceLocation getDefault() {
        return this.animatedResourceLocations.isEmpty() ? null : this.animatedResourceLocations.get(0);
    }
    
    @Override
    public AnimatedFrameResourceLocation getCurrentResourceLocation() {
        return this.getResourceLocationAt(this.currentFrame);
    }
    
    @Override
    public AnimatedFrameResourceLocation getResourceLocationAt(final int position) {
        if (this.animatedResourceLocations.size() - 1 < position) {
            return null;
        }
        return this.animatedResourceLocations.get(position);
    }
    
    @Override
    public void update(final long timestamp) {
        if (!this.prepared) {
            return;
        }
        final long ticks = timestamp / this.delay;
        final int frame = (int)(ticks % this.frameCount);
        this.currentFrame = ((frame < 0 || frame > this.frameCount) ? 0 : frame);
    }
    
    @Override
    public List<AnimatedFrameResourceLocation> getAnimatedResourceLocations() {
        return this.animatedResourceLocations;
    }
    
    private void setPrepared() {
        this.prepared = true;
        if (this.completableListener != null) {
            this.completableListener.run();
        }
        if (this.spriteImage != null) {
            this.spriteImage.close();
        }
        this.spriteImage = null;
    }
    
    private void uploadTextures(final ResourceLocation[] locations) {
        final List<GameImageTexture> textures = new ArrayList<GameImageTexture>();
        final ReferenceStorage references = Laby.references();
        references.asynchronousTextureUploader().prepareAndUploadTexture(() -> {
            int i = 0;
            for (int length = locations.length; i < length; ++i) {
                final ResourceLocation location = locations[i];
                textures.add(references.gameImageTextureFactory().create(location));
            }
        }, () -> {
            textures.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final GameImageTexture texture = iterator.next();
                final ResourceLocation location2 = texture.getTextureResourceLocation();
                this.resources.textureRepository().register(location2, texture);
                this.animatedResourceLocations.add(new DefaultAnimatedFrameResourceLocation(location2));
            }
            textures.clear();
            this.frameCount = locations.length;
            this.setPrepared();
        });
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger("Animated Texture");
    }
    
    private static class DefaultAnimatedFrameResourceLocation implements AnimatedFrameResourceLocation
    {
        private final ResourceLocation delegate;
        
        public DefaultAnimatedFrameResourceLocation(final ResourceLocation delegate) {
            this.delegate = delegate;
        }
        
        @Override
        public <T> T getMinecraftLocation() {
            return this.delegate.getMinecraftLocation();
        }
        
        @Override
        public String getNamespace() {
            return this.delegate.getNamespace();
        }
        
        @Override
        public String getPath() {
            return this.delegate.getPath();
        }
        
        @Override
        public InputStream openStream() throws IOException {
            return this.delegate.openStream();
        }
        
        @Override
        public boolean exists() {
            return this.delegate.exists();
        }
        
        @Override
        public void metadata(final Metadata metadata) {
            this.delegate.metadata(metadata);
        }
        
        @Override
        public Metadata metadata() {
            return this.delegate.metadata();
        }
    }
}
