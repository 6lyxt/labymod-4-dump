// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Laby;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.Widget;

public abstract class ThemeRenderer<T extends Widget> implements WidgetRenderer<T>
{
    protected final LabyAPI labyAPI;
    protected Theme theme;
    protected String name;
    protected final GFXRenderPipeline gfxRenderPipeline;
    protected final RenderPipeline renderPipeline;
    protected final RectangleRenderer rectangleRenderer;
    protected final ComponentRenderer componentRenderer;
    protected final ResourceRenderer resourceRenderer;
    protected final CircleRenderer circleRenderer;
    protected final TextRenderer textRenderer;
    protected final ResourceRenderContext resourceRenderContext;
    
    protected ThemeRenderer(final String name) {
        this.labyAPI = Laby.labyAPI();
        this.name = name;
        this.gfxRenderPipeline = this.labyAPI.gfxRenderPipeline();
        this.renderPipeline = this.labyAPI.renderPipeline();
        this.rectangleRenderer = this.renderPipeline.rectangleRenderer();
        this.componentRenderer = this.renderPipeline.componentRenderer();
        this.resourceRenderer = this.renderPipeline.resourceRenderer();
        this.resourceRenderContext = this.renderPipeline.renderContexts().resourceRenderContext();
        this.circleRenderer = this.renderPipeline.circleRenderer();
        this.textRenderer = this.renderPipeline.textRenderer();
    }
    
    @Override
    public void renderPre(final T widget, final ScreenContext context) {
    }
    
    @Override
    public void renderPost(final T widget, final ScreenContext context) {
    }
    
    public void playInteractionSound(final T widget) {
    }
    
    public boolean hasInteractionSound() {
        return false;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public String getName() {
        return this.name;
    }
    
    public Theme theme() {
        return this.theme;
    }
    
    public void setTheme(final Theme theme) {
        this.theme = theme;
    }
}
