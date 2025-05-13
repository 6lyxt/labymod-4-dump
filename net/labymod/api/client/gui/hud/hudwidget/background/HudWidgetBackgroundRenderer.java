// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.background;

import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.render.matrix.Stack;

public interface HudWidgetBackgroundRenderer
{
    BackgroundConfig config();
    
    default void renderEntireBackground(final Stack stack, final HudSize size) {
        final float width = size.getActualWidth();
        final float height = size.getActualHeight();
        this.renderEntireBackground(stack, width, height);
    }
    
    default void renderEntireBackground(final Stack stack, final float width, final float height) {
        final BackgroundConfig backgroundConfig = this.config();
        final float margin = backgroundConfig.margin().get();
        this.renderBackgroundSegment(stack, margin, margin, width - margin * 2.0f, height - margin * 2.0f);
    }
    
    default void renderBackgroundSegment(final Stack stack, final float x, final float y, final float width, final float height) {
        this.renderBackgroundSegment(stack, x, y, width, height, 1.0f);
    }
    
    default void renderBackgroundSegment(final Stack stack, final float x, final float y, final float width, final float height, final float opacity) {
        final BackgroundConfig backgroundConfig = this.config();
        if (!backgroundConfig.enabled().get()) {
            return;
        }
        final int roundness = backgroundConfig.roundness().get();
        int backgroundColor = backgroundConfig.color().get().get();
        if (opacity != 1.0f) {
            final int rgb = backgroundColor & 0xFFFFFF;
            final float alpha = (backgroundColor >>> 24 & 0xFF) / 255.0f * opacity;
            backgroundColor = ((int)(alpha * 255.0f) << 24 | rgb);
        }
        Laby.labyAPI().renderPipeline().rectangleRenderer().pos(x, y, x + width, y + height).color(backgroundColor).rounded((float)roundness).lowerEdgeSoftness(-0.125f).upperEdgeSoftness(0.5f).render(stack);
    }
}
