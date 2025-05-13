// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import net.labymod.api.util.bounds.Point;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffectRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.TooltipService;

@Singleton
@Implements(TooltipService.class)
public class DefaultTooltipService implements TooltipService
{
    private static final RenderPipeline RENDER_PIPELINE;
    private final HoverBackgroundEffectRenderer hoverEffectRenderer;
    private Object reference;
    private Mouse mouse;
    
    @Inject
    public DefaultTooltipService(final HoverBackgroundEffectRenderer hoverEffectRenderer) {
        this.hoverEffectRenderer = hoverEffectRenderer;
    }
    
    @Override
    public void renderTooltip(final Stack stack, final Widget widget, final Mouse mouse, final RenderableComponent component) {
        if (!LabyConfigProvider.INSTANCE.get().appearance().fixedTooltips().get()) {
            this.render(stack, mouse, component);
            return;
        }
        if (this.reference == widget) {
            this.render(stack, this.mouse, component);
            return;
        }
        this.reference = widget;
        this.mouse = mouse.copy();
    }
    
    @Override
    public void renderFixedTooltip(final Stack stack, final Point point, final RenderableComponent component, final boolean centered) {
        final Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
        final HoverBackgroundEffect effect = currentTheme.getHoverBackgroundRenderer();
        final float scale = effect.getScale();
        final float padding = effect.getPadding();
        final float width = component.getWidth() * scale;
        final float height = component.getCeilHeight() * scale;
        float x = centered ? (point.getX() - width / 2.0f) : ((float)point.getX());
        float y = point.getY() - height - padding;
        final Bounds bounds = Laby.labyAPI().minecraft().minecraftWindow().absoluteBounds();
        if (x + width + padding > bounds.getWidth()) {
            x = bounds.getWidth() - width - padding;
        }
        if (x < 0.0f) {
            x = padding;
        }
        if (y + height + padding > bounds.getHeight()) {
            y = bounds.getHeight() - height - padding;
        }
        if (y < 0.0f) {
            y = padding;
        }
        this.hoverEffectRenderer.component(component).hoverEffect(effect).pos(x, y, component.getWidth() * scale, component.getCeilHeight() * scale).render(stack);
    }
    
    @Override
    public void unhover(final Widget widget) {
        if (this.reference == widget) {
            this.reference = null;
            this.mouse = null;
        }
    }
    
    private void render(final Stack stack, final Mouse mouse, final RenderableComponent component) {
        DefaultTooltipService.RENDER_PIPELINE.componentRenderer().renderHoverComponent(stack, mouse, component, -1, true);
    }
    
    static {
        RENDER_PIPELINE = Laby.labyAPI().renderPipeline();
    }
}
