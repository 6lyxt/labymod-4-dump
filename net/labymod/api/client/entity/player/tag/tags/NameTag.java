// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.tags;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.entity.player.tag.event.NameTagBackgroundRenderEvent;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.LabyAPI;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer;

public abstract class NameTag extends AbstractTagRenderer
{
    private static final int PADDING = 1;
    private final ComponentRenderer componentRenderer;
    private final RectangleRenderer rectangleRenderer;
    private final MinecraftOptions options;
    @Nullable
    private RenderableComponent renderableComponent;
    
    protected NameTag() {
        final LabyAPI api = Laby.labyAPI();
        final RenderPipeline renderPipeline = api.renderPipeline();
        this.componentRenderer = renderPipeline.componentRenderer();
        this.rectangleRenderer = renderPipeline.rectangleRenderer();
        this.options = api.minecraft().options();
    }
    
    @Override
    public void begin(final Entity entity) {
        super.begin(entity);
        this.renderableComponent = this.getRenderableComponent();
    }
    
    @Override
    public boolean isVisible() {
        return this.renderableComponent != null;
    }
    
    @Override
    public void render(final Stack stack, final Entity entity) {
        final RenderableComponent renderableComponent = this.renderableComponent;
        if (renderableComponent == null) {
            return;
        }
        if (PlatformEnvironment.isAncientOpenGL()) {
            this.renderLegacyTag(stack, entity, renderableComponent);
        }
        else {
            this.renderModernTag(stack, entity, renderableComponent);
        }
    }
    
    private void drawNameplateBackground(final Stack stack) {
        final NameTagBackgroundRenderEvent event = Laby.fireEvent(NameTagBackgroundRenderEvent.singleton());
        if (event.isCancelled()) {
            return;
        }
        final NameTagBackground background = this.getCustomBackground();
        if (background != null && !background.isEnabled()) {
            return;
        }
        final int alpha = (int)(this.options.getBackgroundColorWithOpacity(192) * 255.0f);
        final int backgroundColor = ColorFormat.ARGB32.pack((background == null) ? event.getColor() : background.getColor(), alpha);
        final float height = this.getHeight();
        this.renderBackground(stack, -1.0f, 0.0f, this.getWidth(), height, backgroundColor);
    }
    
    private void renderLegacyTag(final Stack stack, final Entity entity, final RenderableComponent renderableComponent) {
        final boolean discrete = !this.isDiscrete(entity);
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        gfx.depthMask(false);
        if (this.withDepthTest() && discrete) {
            gfx.disableDepth();
        }
        gfx.enableBlend();
        gfx.defaultBlend();
        this.drawNameplateBackground(stack);
        gfx.depthMask(true);
        if (!discrete) {
            gfx.enableDepth();
        }
        this.renderText(stack, renderableComponent, discrete, 553648127, 0);
        gfx.enableDepth();
        if (discrete) {
            this.renderText(stack, renderableComponent, false, -1, 0);
        }
        gfx.restoreBlaze3DStates();
    }
    
    protected void renderModernTag(final Stack stack, final Entity entity, final RenderableComponent renderableComponent) {
        final boolean discrete = !this.isDiscrete(entity);
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        gfx.depthMask(false);
        if (!discrete) {
            gfx.enableDepth();
        }
        final boolean withDepthTest = this.withDepthTest();
        if (!withDepthTest) {
            gfx.enableDepth();
        }
        gfx.depthMask(true);
        if (withDepthTest) {
            final NameTagBackground background = this.getCustomBackground();
            final int alpha = (int)(this.options.getBackgroundColorWithOpacity(192) * 255.0f);
            int backgroundColor = 0;
            if (background != null) {
                if (background.isEnabled()) {
                    backgroundColor = background.getColor();
                }
            }
            else {
                backgroundColor = ColorFormat.ARGB32.pack(backgroundColor, alpha);
            }
            this.renderText(stack, renderableComponent, discrete, 553648127, backgroundColor);
        }
        if (discrete) {
            this.renderText(stack, renderableComponent, false, -1, 0);
        }
        gfx.restoreBlaze3DStates();
    }
    
    protected void renderBackground(final Stack stack, final float x, final float y, final float width, final float height, final int backgroundColor) {
        this.rectangleRenderer.renderRectangle(stack, x, y, width, height, backgroundColor);
    }
    
    private void renderText(final Stack stack, final RenderableComponent component, final boolean discrete, final int textColor, final int backgroundColor) {
        this.renderText(stack, component, discrete, textColor, backgroundColor, 1.0f, 0.5f);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.6")
    protected void renderText(final Stack stack, final RenderableComponent component, final boolean discrete, final int textColor, final float x, final float y) {
        this.renderText(stack, component, discrete, textColor, 0, x, y);
    }
    
    protected void renderText(final Stack stack, final RenderableComponent component, final boolean discrete, final int textColor, final int backgroundColor, final float x, final float y) {
        this.componentRenderer.builder().shadow(false).discrete(discrete).pos(x, y).useFloatingPointPosition(true).color(textColor).allowColors(true).text(component).shouldBatch(false).backgroundColor(backgroundColor).render(stack);
    }
    
    @Override
    public float getWidth() {
        final RenderableComponent component = this.renderableComponent;
        return (component == null) ? 0.0f : (component.getWidth() + 2.0f);
    }
    
    @Override
    public float getHeight() {
        final float height = this.componentRenderer.height();
        if (this.renderableComponent == null) {
            return height;
        }
        final float componentHeight = this.renderableComponent.getHeight();
        if (componentHeight > height) {
            return componentHeight - height;
        }
        return componentHeight;
    }
    
    @Override
    public float getScale() {
        return 1.0f;
    }
    
    @Nullable
    protected RenderableComponent getRenderableComponent() {
        return null;
    }
    
    protected boolean withDepthTest() {
        return true;
    }
    
    protected NameTagBackground getCustomBackground() {
        return null;
    }
}
