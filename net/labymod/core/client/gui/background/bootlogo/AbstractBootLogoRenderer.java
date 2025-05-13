// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.bootlogo;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.LabyAPI;

public abstract class AbstractBootLogoRenderer
{
    protected final LabyAPI labyAPI;
    protected final RectangleRenderer rectangleRenderer;
    protected float timePassed;
    protected boolean resourcesPreloaded;
    protected float progress;
    protected boolean progressVisible;
    
    protected AbstractBootLogoRenderer() {
        this.labyAPI = Laby.labyAPI();
        this.rectangleRenderer = this.labyAPI.renderPipeline().rectangleRenderer();
    }
    
    public void updateProgress(final float progress, final boolean visible) {
        this.progress = (float)MathHelper.clamp(this.progress * 0.95 + progress * 0.05, 0.0, 1.0);
        this.progressVisible = visible;
    }
    
    public void initialize() {
        this.timePassed = 0.0f;
        this.progress = 0.0f;
    }
    
    public void render(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta) {
        this.renderBackground(stack, left, top, right, bottom, tickDelta);
        this.renderForeground(stack, left, top, right, bottom, tickDelta);
    }
    
    public abstract void renderBackground(final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5);
    
    public abstract void renderForeground(final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final boolean p6);
    
    public void renderForeground(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta) {
        this.renderForeground(stack, left, top, right, bottom, tickDelta, true);
    }
    
    public void preloadResources() {
        if (this.resourcesPreloaded) {
            return;
        }
        this.onPreloadResources();
        this.resourcesPreloaded = true;
    }
    
    protected void preloadTexture(final ResourceLocation resourceLocation) {
        Laby.references().textureRepository().preloadTexture(resourceLocation);
    }
    
    protected abstract void onPreloadResources();
    
    public boolean isResourcesPreloaded() {
        return this.resourcesPreloaded;
    }
}
