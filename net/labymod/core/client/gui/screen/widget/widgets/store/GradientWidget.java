// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.store;

import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class GradientWidget extends SimpleWidget
{
    private LssProperty<Direction> direction;
    private LssProperty<Integer> colorStart;
    private LssProperty<Integer> colorEnd;
    
    public GradientWidget() {
        this.direction = new LssProperty<Direction>(null);
        this.colorStart = new LssProperty<Integer>(0);
        this.colorEnd = new LssProperty<Integer>(0);
    }
    
    @Override
    public void render(final ScreenContext context) {
        final RectangleRenderer rectangleRenderer = this.labyAPI.renderPipeline().rectangleRenderer().pos(this.bounds());
        final BorderRadius borderRadius = this.getBorderRadius();
        if (borderRadius != null) {
            rectangleRenderer.rounded(borderRadius.getLeftTop(), borderRadius.getRightTop(), borderRadius.getLeftBottom(), borderRadius.getRightBottom());
        }
        final Integer topColor = this.colorStart.get();
        final Direction direction = this.direction.get();
        if (direction == null) {
            rectangleRenderer.color(topColor).render(context.stack());
            return;
        }
        final Integer bottomColor = this.colorEnd.get();
        switch (direction.ordinal()) {
            case 0: {
                rectangleRenderer.gradientVertical(topColor, bottomColor);
                break;
            }
            case 1: {
                rectangleRenderer.gradientVertical(bottomColor, topColor);
                break;
            }
            case 2: {
                rectangleRenderer.gradientHorizontal(topColor, bottomColor);
                break;
            }
            case 3: {
                rectangleRenderer.gradientHorizontal(bottomColor, topColor);
                break;
            }
        }
        rectangleRenderer.render(context.stack());
    }
    
    public LssProperty<Direction> direction() {
        return this.direction;
    }
    
    public LssProperty<Integer> colorStart() {
        return this.colorStart;
    }
    
    public LssProperty<Integer> colorEnd() {
        return this.colorEnd;
    }
    
    public enum Direction
    {
        VERTICAL_TOP_BOTTOM, 
        VERTICAL_BOTTOM_TOP, 
        HORIZONTAL_LEFT_RIGHT, 
        HORIZONTAL_RIGHT_LEFT;
    }
}
