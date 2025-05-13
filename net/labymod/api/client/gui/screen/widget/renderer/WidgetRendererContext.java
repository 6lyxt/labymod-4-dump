// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.renderer;

import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.LabyAPI;

public class WidgetRendererContext
{
    private final LabyAPI labyAPI;
    private final AbstractWidget<?> widget;
    private WidgetRenderer.RenderPhase renderPhase;
    private ThemeRenderer themeRenderer;
    
    public WidgetRendererContext(final AbstractWidget<?> widget) {
        this.labyAPI = Laby.labyAPI();
        this.widget = widget;
    }
    
    public void setThemeRenderer(final ThemeRenderer themeRenderer) {
        this.themeRenderer = themeRenderer;
    }
    
    public void renderPre(final ScreenContext context) {
        this.renderPhase = WidgetRenderer.RenderPhase.PRE;
        this.applyFilters(context);
        this.invokeWidgetRenderer(this.themeRenderer, context);
        this.invokeWidgetRenderer(this.widget.shadow, context);
    }
    
    public void renderWidget(final ScreenContext context) {
        final boolean scissorX = this.widget.stencilX().get();
        final boolean scissorY = this.widget.stencilY().get();
        final boolean hasScissor = scissorX || scissorY;
        final Scissor scissor = this.labyAPI.gfxRenderPipeline().scissor();
        try {
            if (hasScissor) {
                final Window window = this.labyAPI.minecraft().minecraftWindow();
                final Rectangle windowBounds = window.absoluteBounds();
                final Rectangle widgetBounds = this.widget.bounds().rectangle(BoundsType.MIDDLE);
                final float x = (scissorX ? widgetBounds : windowBounds).getX();
                final float y = (scissorY ? widgetBounds : windowBounds).getY();
                final float width = scissorX ? widgetBounds.getWidth() : ((float)window.getRawWidth());
                final float height = scissorY ? widgetBounds.getHeight() : ((float)window.getRawHeight());
                scissor.push(context.stack(), x, y, width, height, this.widget.useFloatingPointPosition().get());
            }
            this.themeRenderer.renderWidget(this.widget, context);
        }
        finally {
            if (hasScissor) {
                scissor.pop();
            }
        }
    }
    
    public void renderPost(final ScreenContext context) {
        this.renderPhase = WidgetRenderer.RenderPhase.POST;
        this.invokeWidgetRenderer(this.themeRenderer, context);
        this.invokeWidgetRenderer(this.widget.shadow, context);
        this.applyFilters(context);
    }
    
    private void applyFilters(final ScreenContext context) {
        final Filter[] filters = this.widget.filter().get();
        if (filters == null) {
            return;
        }
        for (final Filter filter : filters) {
            this.invokeWidgetRenderer(filter, context);
        }
    }
    
    private void invokeWidgetRenderer(final WidgetRenderer<AbstractWidget<?>> renderer, final ScreenContext context) {
        if (renderer == null) {
            return;
        }
        if (this.renderPhase == WidgetRenderer.RenderPhase.PRE) {
            renderer.renderPre(this.widget, context);
        }
        else {
            renderer.renderPost(this.widget, context);
        }
    }
}
