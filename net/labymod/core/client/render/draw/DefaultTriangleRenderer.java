// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.api.util.ColorUtil;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.batch.TriangleRenderContext;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.TriangleRenderer;

@Singleton
@Implements(TriangleRenderer.class)
public class DefaultTriangleRenderer implements TriangleRenderer
{
    private final TriangleRenderContext triangleRenderContext;
    
    @Inject
    public DefaultTriangleRenderer(final TriangleRenderContext triangleRenderContext) {
        this.triangleRenderContext = triangleRenderContext;
    }
    
    @Override
    public void render(@NotNull final Stack stack, final float x, final float y, final float x1, final float y1, final float x2, final float y2, final int color, final boolean filled) {
        if (ColorUtil.isNoColor(color)) {
            return;
        }
        this.triangleRenderContext.begin(stack, filled).render(x, y, x1, y1, x2, y2, color).getBuilder().uploadToBuffer();
    }
    
    @Override
    public void renderUnderlined(@NotNull final Stack stack, final float x, final float y, final float x1, final float y1, final float x2, final float y2, final int color, final int borderColor, final float borderWidth) {
        this.render(stack, x, y, x1, y1, x2, y2, color, true);
        if (ColorUtil.isNoColor(borderColor)) {
            return;
        }
        this.triangleRenderContext.begin(stack, false, borderWidth).renderLine(x, y, x1, y1, borderColor);
        this.triangleRenderContext.getBuilder().uploadToBuffer();
    }
    
    @Override
    public void renderBordered(@NotNull final Stack stack, final float x, final float y, final float x1, final float y1, final float x2, final float y2, final int color, final int borderColor, final float borderWidth) {
        this.render(stack, x, y, x1, y1, x2, y2, color, true);
        if (ColorUtil.isNoColor(borderColor)) {
            return;
        }
        this.triangleRenderContext.begin(stack, false, borderWidth).render(x, y, x1, y1, x2, y2, borderColor);
        this.triangleRenderContext.getBuilder().uploadToBuffer();
    }
    
    @Override
    public void renderTrapezoid(@NotNull final Stack stack, final float x, final float y, final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final int color, final boolean filled) {
        if (ColorUtil.isNoColor(color)) {
            return;
        }
        this.triangleRenderContext.begin(stack, filled).render(x, y, x1, y1, x2, y2, x3, y3, color).getBuilder().uploadToBuffer();
    }
    
    @Override
    public void renderTrapezoidBordered(@NotNull final Stack stack, final float x, final float y, final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final int color, final int borderColor, final float borderWidth) {
        this.renderTrapezoid(stack, x, y, x1, y1, x2, y2, x3, y3, color, true);
        if (ColorUtil.isNoColor(borderColor)) {
            return;
        }
        this.triangleRenderContext.begin(stack, false, borderWidth).render(x, y, x1, y1, x2, y2, x3, y3, borderColor);
        this.triangleRenderContext.getBuilder().uploadToBuffer();
    }
}
