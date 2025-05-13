// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.RectangleState;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;

public interface Bounds extends ReasonableMutableRectangle
{
    void checkForChanges();
    
    void setOuterWidth(final float p0, final ModifyReason p1);
    
    void setOuterHeight(final float p0, final ModifyReason p1);
    
    void setMiddleWidth(final float p0, final ModifyReason p1);
    
    void setMiddleHeight(final float p0, final ModifyReason p1);
    
    void setOuterX(final float p0, final ModifyReason p1);
    
    void setOuterY(final float p0, final ModifyReason p1);
    
    void setOuterLeft(final float p0, final ModifyReason p1);
    
    void setOuterTop(final float p0, final ModifyReason p1);
    
    void setOuterRight(final float p0, final ModifyReason p1);
    
    void setOuterBottom(final float p0, final ModifyReason p1);
    
    void setOuterPosition(final float p0, final float p1, final ModifyReason p2);
    
    void setOuterSize(final float p0, final float p1, final ModifyReason p2);
    
    void setMiddleLeft(final float p0, final ModifyReason p1);
    
    void setMiddleTop(final float p0, final ModifyReason p1);
    
    void setMiddleRight(final float p0, final ModifyReason p1);
    
    void setMiddleBottom(final float p0, final ModifyReason p1);
    
    void setX(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setY(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setRightX(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setBottomY(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setLeft(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setTop(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setBottom(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setRight(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setPosition(final float p0, final float p1, final BoundsType p2, final ModifyReason p3);
    
    void setSize(final float p0, final float p1, final BoundsType p2, final ModifyReason p3);
    
    void setWidth(final float p0, final BoundsType p1, final ModifyReason p2);
    
    void setHeight(final float p0, final BoundsType p1, final ModifyReason p2);
    
    float getX(final BoundsType p0);
    
    float getY(final BoundsType p0);
    
    float getWidth(final BoundsType p0);
    
    float getHeight(final BoundsType p0);
    
    float getLeft(final BoundsType p0);
    
    float getTop(final BoundsType p0);
    
    float getRight(final BoundsType p0);
    
    float getBottom(final BoundsType p0);
    
    float getMaxX(final BoundsType p0);
    
    float getMaxY(final BoundsType p0);
    
    float getCenterX(final BoundsType p0);
    
    float getCenterY(final BoundsType p0);
    
    float getOffset(final BoundsType p0, final OffsetSide p1);
    
    float getOffset(final BoundsType p0, final RectangleState p1);
    
    float getHorizontalOffset(final BoundsType p0);
    
    float getVerticalOffset(final BoundsType p0);
    
    boolean isInRectangle(final BoundsType p0, final Point p1);
    
    boolean isInRectangle(final BoundsType p0, final float p1, final float p2);
    
    boolean isXInRectangle(final BoundsType p0, final float p1);
    
    boolean isYInRectangle(final BoundsType p0, final float p1);
    
    ReasonableMutableRectangle rectangle(final BoundsType p0);
    
    MutableRectangle copy(final BoundsType p0);
    
    MutableRectangle copy(final BoundsType p0, final MutableRectangle p1);
    
    WidgetStyleSheetUpdater getUpdater();
    
    Rectangle prevRectangle();
    
    boolean wasUpdatedThisFrame();
    
    default MutableRectangle absoluteBounds(final Parent parent) {
        return absoluteBounds(parent, BoundsType.INNER);
    }
    
    default MutableRectangle absoluteBounds(Parent parent, final BoundsType boundsType) {
        final MutableRectangle rectangle = parent.bounds().rectangle(boundsType).copy();
        while ((parent = parent.getParent()) != null) {
            if (parent instanceof ScreenRendererWidget) {
                rectangle.setPosition(rectangle.getX() + parent.bounds().getX(BoundsType.INNER), rectangle.getY() + parent.bounds().getY(BoundsType.INNER));
            }
            if (parent instanceof final Widget widget) {
                rectangle.add(widget.getTranslateX(), widget.getTranslateY());
            }
        }
        return rectangle;
    }
}
