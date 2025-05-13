// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.rplace;

import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.client.resources.texture.Texture;
import java.net.URI;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.ResourceLocation;

public class RPlaceMapRenderer
{
    private static final long UPDATE_INTERVAL = 60000L;
    private final RPlaceRegistry rPlaceRegistry;
    private long timeLastUpdated;
    private ResourceLocation resourceLocation;
    private boolean textureAvailable;
    private float aspectRatio;
    
    public RPlaceMapRenderer() {
        this.rPlaceRegistry = LabyMod.references().rPlaceRegistry();
        this.timeLastUpdated = 0L;
        this.aspectRatio = 1.0f;
    }
    
    public void render(final Stack stack, final float x, final float y, final float width, final float height) {
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        final RenderPipeline renderPipeline = Laby.labyAPI().renderPipeline();
        final float border = 1.0f;
        renderPipeline.rectangleRenderer().renderRectangle(stack, x, y, x + width, y + height, Integer.MIN_VALUE);
        if (this.isTextureAvailable()) {
            stack.push();
            stack.translate(0.0f, 0.0f, 500.0f);
            renderPipeline.resourceRenderer().texture(this.resourceLocation).sprite(0.0f, 0.0f, 1.0f, 1.0f).resolution(1.0f, 1.0f).pos(x + border, y + border).size(width - border * 2.0f, height - border * 2.0f).render(stack);
            stack.pop();
        }
        else {
            renderPipeline.componentRenderer().builder().pos(x + width / 2.0f, y + height / 2.0f).text(Component.translatable("labymod.misc.loading", new Component[0])).centered(true).render(stack);
        }
    }
    
    public void update() {
        final long timePassed = TimeUtil.getMillis() - this.timeLastUpdated;
        if (timePassed <= 60000L) {
            return;
        }
        this.timeLastUpdated = TimeUtil.getMillis();
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                String url = this.rPlaceRegistry.getMapUrl();
                if (url.contains("%s")) {
                    url = String.format(url, TimeUtil.getMillis());
                }
                final InputStream in = URI.create(url).toURL().openStream();
                final GameImage gameImage = Laby.references().gameImageProvider().getImage(in);
                if (gameImage != null) {
                    this.aspectRatio = gameImage.getWidth() / (float)gameImage.getHeight();
                    final Resources resources = Laby.labyAPI().renderPipeline().resources();
                    final TextureRepository repository = resources.textureRepository();
                    Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                        if (this.resourceLocation == null) {
                            this.resourceLocation = resources.resourceLocationFactory().create("labymod", "pixelart/map.png");
                        }
                        else {
                            repository.releaseTexture(this.resourceLocation);
                            this.textureAvailable = false;
                        }
                        final GameImageTexture texture = resources.gameImageTextureFactory().create(this.resourceLocation, gameImage);
                        repository.register(this.resourceLocation, texture);
                        this.textureAvailable = true;
                    });
                }
            }
            catch (final Exception e) {
                e.printStackTrace();
                this.textureAvailable = false;
            }
        });
    }
    
    public boolean isFeatureAvailable() {
        return this.rPlaceRegistry.isEnabled() && this.rPlaceRegistry.isOnTargetLobby() && this.rPlaceRegistry.getMapUrl() != null;
    }
    
    public boolean isTextureAvailable() {
        return this.textureAvailable && this.resourceLocation != null && this.isFeatureAvailable();
    }
    
    public RPlaceRegistry getRegistry() {
        return this.rPlaceRegistry;
    }
    
    public float getAspectRatio() {
        if (this.aspectRatio == 0.0f) {
            return 1.0f;
        }
        return this.aspectRatio;
    }
}
