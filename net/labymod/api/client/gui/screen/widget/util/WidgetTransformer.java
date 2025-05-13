// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.util;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class WidgetTransformer
{
    private final AbstractWidget<?> widget;
    private final FloatVector2 pivot;
    
    public WidgetTransformer(final AbstractWidget<?> widget) {
        this.pivot = new FloatVector2();
        this.widget = widget;
    }
    
    public float transformMouseX(float mouseX, final float pivotX) {
        mouseX -= this.widget.translateX().get() + pivotX;
        mouseX /= this.widget.scaleX().get();
        mouseX += pivotX;
        return mouseX;
    }
    
    public float transformMouseY(float mouseY, final float pivotY) {
        mouseY -= this.widget.translateY().get() + pivotY;
        mouseY /= this.widget.scaleY().get();
        mouseY += pivotY;
        return mouseY;
    }
    
    public FloatVector2 getPivot() {
        final Bounds bounds = this.widget.bounds();
        this.pivot.set(this.getAlignmentX(bounds), this.getAlignmentY(bounds));
        return this.pivot;
    }
    
    public void applyStackManipulation(final ScreenContext context, final float x, final float y, final FloatVector2 pivot) {
        final float offsetX = pivot.getX();
        final float offsetY = pivot.getY();
        context.translate(this.widget.translateX().get() + x, this.widget.translateY().get() + y, this.widget.zIndex().get());
        context.translate(offsetX, offsetY, 0.0f);
        context.scale(this.widget.scaleX().get(), this.widget.scaleY().get(), 1.0f);
        context.translate(-offsetX, -offsetY, 0.0f);
        context.translate(-x, -y, 0.0f);
    }
    
    private float getAlignmentX(final Bounds bounds) {
        return switch (this.widget.alignmentX().get()) {
            case CENTER -> bounds.getWidth(BoundsType.OUTER) / 2.0f;
            case RIGHT -> bounds.getWidth(BoundsType.OUTER);
            default -> 0.0f;
        };
    }
    
    private float getAlignmentY(final Bounds bounds) {
        return switch (this.widget.alignmentY().get()) {
            case CENTER -> bounds.getHeight(BoundsType.OUTER) / 2.0f;
            case BOTTOM -> bounds.getHeight(BoundsType.OUTER);
            default -> 0.0f;
        };
    }
}
