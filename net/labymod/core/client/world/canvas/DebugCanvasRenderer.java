// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.canvas;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.util.Color;
import net.labymod.api.Laby;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.canvas.CanvasRenderer;

public class DebugCanvasRenderer implements CanvasRenderer
{
    private static final Component DEBUG_TEXT;
    
    @Override
    public void render2D(final Stack stack, final MutableMouse mouse, final Canvas canvas, final CanvasSide side, final float tickDelta) {
        Laby.labyAPI().renderPipeline().rectangleRenderer().setupGradient(stack, ctx -> ctx.renderGradientVertically(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), Color.RED.get(), Color.BLUE.get()));
    }
    
    @Override
    public void renderOverlay2D(final Stack stack, final MutableMouse mouse, final Canvas canvas, final CanvasSide side, final float tickDelta) {
        final ComponentRenderer renderer = Laby.references().componentRenderer();
        final RenderableComponent component = RenderableComponent.of(DebugCanvasRenderer.DEBUG_TEXT, canvas.getWidth() - 10.0f, HorizontalAlignment.CENTER);
        renderer.builder().text(component).pos(canvas.getWidth() / 2.0f, canvas.getHeight() / 2.0f - component.getHeight() / 2.0f).centered(true).render(stack);
    }
    
    @Override
    public void dispose(final Canvas canvas) {
    }
    
    static {
        DEBUG_TEXT = ((BaseComponent<Component>)Component.text("Debug ")).append(Component.text("Debug", NamedTextColor.GREEN));
    }
}
