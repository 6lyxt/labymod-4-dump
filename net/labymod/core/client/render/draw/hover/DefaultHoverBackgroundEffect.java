// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.hover;

import net.labymod.api.client.render.RenderPipeline;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;

public abstract class DefaultHoverBackgroundEffect implements HoverBackgroundEffect
{
    protected final RectangleRenderer rectangleRenderer;
    protected final ComponentRendererBuilder componentRendererBuilder;
    
    protected DefaultHoverBackgroundEffect(@NotNull final LabyAPI labyAPI) {
        final RenderPipeline renderPipeline = labyAPI.renderPipeline();
        this.rectangleRenderer = renderPipeline.rectangleRenderer();
        this.componentRendererBuilder = renderPipeline.componentRenderer().builder();
    }
}
