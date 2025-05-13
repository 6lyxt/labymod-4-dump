// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.vertex.shard.RenderShards;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import net.labymod.api.client.render.draw.HeartRenderer;

@Implements(HeartRenderer.class)
public class DefaultHeartRenderer implements HeartRenderer
{
    private static final float HEART_SIZE = 9.0f;
    private static final float HEART_PIXEL_SIZE = 0.11111111f;
    private static final float HEART_PIXEL_UI_OFFSET = 0.8888889f;
    private static final float HEART_PIXEL_3D_OFFSET = 0.875f;
    private static final long FLASHING_DELAY = 100L;
    private static final long FLASHING_TIME = 185L;
    private long flashingStartTime;
    private int flashingTimes;
    
    @Inject
    public DefaultHeartRenderer() {
    }
    
    protected static HeartRenderer create() {
        return new DefaultHeartRenderer();
    }
    
    @Override
    public void renderHealthBar(final Stack stack, final float startX, final float y, final int size, final int health, final int maxHealth) {
        this.renderHealthBar(stack, startX, y, size, health, maxHealth, 0);
    }
    
    @Override
    public void renderHealthBar(final Stack stack, final float startX, final float y, final int size, final int health, final int maxHealth, final int absorption) {
        final ResourceRenderContext renderer = this.getResourceRenderContext(stack);
        final HeartTexture backgroundTexture = this.getBackgroundTexture();
        this.renderHearts(backgroundTexture, renderer, startX, y, size, maxHealth / 2);
        final float actualSize = this.getActualSize(size);
        for (int i = 1; i <= maxHealth + absorption; ++i) {
            if (i % 2 == 0) {
                final float x = startX + actualSize / 2.0f * (i - 2);
                if (i <= health) {
                    this.renderSingleHeart(HeartTexture.FULL_HEART, renderer, x, y, size);
                }
                else if (i > maxHealth) {
                    this.renderSingleHeart(backgroundTexture, renderer, x, y, size);
                    this.renderSingleHeart(HeartTexture.FULL_ABSORPTION_HEART, renderer, x, y, size);
                }
            }
            else {
                final float x = startX + actualSize / 2.0f * (i - 1);
                if (i == health) {
                    this.renderSingleHeart(HeartTexture.HALF_HEART, renderer, x, y, size);
                }
                else if (i > maxHealth && maxHealth + absorption == i) {
                    this.renderSingleHeart(backgroundTexture, renderer, x, y, size);
                    this.renderSingleHeart(HeartTexture.HALF_ABSORPTION_HEART, renderer, x, y, size);
                }
            }
        }
        final boolean legacyVersion = PlatformEnvironment.isAncientOpenGL();
        if (legacyVersion) {
            RenderShards.LEGACY_DEPTH_TEST.setupShared();
        }
        renderer.uploadToBuffer();
        if (legacyVersion) {
            RenderShards.LEGACY_DEPTH_TEST.finishShared();
        }
    }
    
    @Override
    public void renderHearts(final HeartTexture heartTexture, final Stack stack, final float startX, final float y, final int size, final int heartAmount) {
        final float actualSize = this.getActualSize(size);
        for (int i = 0; i < heartAmount; ++i) {
            heartTexture.render(stack, startX + i * actualSize, y, (float)size);
        }
    }
    
    @Override
    public void renderHearts(final HeartTexture heartTexture, final ResourceRenderContext context, final float startX, final float y, final int size, final int heartAmount) {
        final float actualSize = this.getActualSize(size);
        for (int i = 0; i < heartAmount; ++i) {
            heartTexture.render(context, startX + i * actualSize, y, (float)size);
        }
    }
    
    @Override
    public void startFlashing(final int times) {
        this.flashingTimes = times;
        this.flashingStartTime = TimeUtil.getMillis();
    }
    
    @Override
    public void stopFlashing() {
        this.flashingTimes = 0;
    }
    
    @Override
    public boolean isFlashing() {
        return this.flashingTimes != 0;
    }
    
    @Override
    public boolean isCurrentlyFlashing() {
        final long currentTime = TimeUtil.getMillis();
        if (!this.isFlashing() || this.flashingStartTime > currentTime) {
            return false;
        }
        if (this.flashingStartTime < currentTime) {
            final long flashingEndTime = this.flashingStartTime + 185L;
            if (flashingEndTime > currentTime) {
                return true;
            }
            --this.flashingTimes;
            this.flashingStartTime = currentTime + 100L;
        }
        return false;
    }
    
    @Override
    public int getWidth(final int hearts, final int size) {
        return (int)(hearts * this.getActualSize(size) / 2.0f);
    }
    
    private float getActualSize(final int size) {
        if (Laby.references().renderEnvironmentContext().isScreenContext()) {
            return size * 0.8888889f;
        }
        return size * 0.875f;
    }
    
    private ResourceRenderContext getResourceRenderContext(final Stack stack) {
        final ResourceLocation iconsTexture = Laby.labyAPI().minecraft().textures().iconsTexture();
        final ResourceRenderContext context = Laby.labyAPI().renderPipeline().renderContexts().resourceRenderContext();
        return context.begin(stack, iconsTexture);
    }
}
