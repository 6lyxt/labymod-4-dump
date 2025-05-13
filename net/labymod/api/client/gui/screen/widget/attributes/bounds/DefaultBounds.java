// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.RectangleState;
import net.labymod.api.util.bounds.ModifyReason;

public interface DefaultBounds extends Bounds
{
    default void setOuterWidth(final float width, final ModifyReason reason) {
        this.setWidth(width - this.getOffset(BoundsType.OUTER, OffsetSide.LEFT) - this.getOffset(BoundsType.OUTER, OffsetSide.RIGHT), reason);
    }
    
    default void setOuterHeight(final float height, final ModifyReason reason) {
        this.setHeight(height - this.getOffset(BoundsType.OUTER, OffsetSide.TOP) - this.getOffset(BoundsType.OUTER, OffsetSide.BOTTOM), reason);
    }
    
    default void setMiddleWidth(final float width, final ModifyReason reason) {
        this.setWidth(width - this.getOffset(BoundsType.MIDDLE, OffsetSide.LEFT) - this.getOffset(BoundsType.MIDDLE, OffsetSide.RIGHT), reason);
    }
    
    default void setMiddleHeight(final float height, final ModifyReason reason) {
        this.setHeight(height - this.getOffset(BoundsType.MIDDLE, OffsetSide.TOP) - this.getOffset(BoundsType.MIDDLE, OffsetSide.BOTTOM), reason);
    }
    
    default void setOuterX(final float x, final ModifyReason reason) {
        this.setX(x + this.getOffset(BoundsType.OUTER, OffsetSide.LEFT), reason);
    }
    
    default void setOuterY(final float y, final ModifyReason reason) {
        this.setY(y + this.getOffset(BoundsType.OUTER, OffsetSide.TOP), reason);
    }
    
    default void setOuterLeft(final float left, final ModifyReason reason) {
        this.setLeft(left + this.getOffset(BoundsType.OUTER, OffsetSide.LEFT), reason);
    }
    
    default void setOuterTop(final float top, final ModifyReason reason) {
        this.setTop(top + this.getOffset(BoundsType.OUTER, OffsetSide.TOP), reason);
    }
    
    default void setOuterRight(final float right, final ModifyReason reason) {
        this.setRight(right - this.getOffset(BoundsType.OUTER, OffsetSide.RIGHT), reason);
    }
    
    default void setOuterBottom(final float bottom, final ModifyReason reason) {
        this.setBottom(bottom - this.getOffset(BoundsType.OUTER, OffsetSide.BOTTOM), reason);
    }
    
    default void setOuterPosition(final float x, final float y, final ModifyReason reason) {
        this.setOuterX(x, reason);
        this.setOuterY(y, reason);
    }
    
    default void setOuterSize(final float width, final float height, final ModifyReason reason) {
        this.setOuterWidth(width, reason);
        this.setOuterHeight(height, reason);
    }
    
    default void setMiddleLeft(final float left, final ModifyReason reason) {
        this.setLeft(left, BoundsType.MIDDLE, reason);
    }
    
    default void setMiddleTop(final float top, final ModifyReason reason) {
        this.setTop(top, BoundsType.MIDDLE, reason);
    }
    
    default void setMiddleRight(final float right, final ModifyReason reason) {
        this.setRight(right, BoundsType.MIDDLE, reason);
    }
    
    default void setMiddleBottom(final float bottom, final ModifyReason reason) {
        this.setBottom(bottom, BoundsType.MIDDLE, reason);
    }
    
    default void setX(final float x, final BoundsType type, final ModifyReason reason) {
        this.setX(x + this.getOffset(type, OffsetSide.LEFT), reason);
    }
    
    default void setY(final float y, final BoundsType type, final ModifyReason reason) {
        this.setY(y + this.getOffset(type, OffsetSide.TOP), reason);
    }
    
    default void setRightX(final float x, final BoundsType type, final ModifyReason reason) {
        this.setRightX(x - this.getOffset(type, OffsetSide.RIGHT), reason);
    }
    
    default void setBottomY(final float y, final BoundsType type, final ModifyReason reason) {
        this.setBottomY(y - this.getOffset(type, OffsetSide.BOTTOM), reason);
    }
    
    default void setLeft(final float left, final BoundsType type, final ModifyReason reason) {
        this.setLeft(left + this.getOffset(type, OffsetSide.LEFT), reason);
    }
    
    default void setTop(final float top, final BoundsType type, final ModifyReason reason) {
        this.setTop(top + this.getOffset(type, OffsetSide.TOP), reason);
    }
    
    default void setBottom(final float bottom, final BoundsType type, final ModifyReason reason) {
        this.setBottom(bottom - this.getOffset(type, OffsetSide.BOTTOM), reason);
    }
    
    default void setRight(final float right, final BoundsType type, final ModifyReason reason) {
        this.setRight(right - this.getOffset(type, OffsetSide.RIGHT), reason);
    }
    
    default void setPosition(final float x, final float y, final BoundsType type, final ModifyReason reason) {
        this.setX(x, type, reason);
        this.setY(y, type, reason);
    }
    
    default void setSize(final float width, final float height, final BoundsType type, final ModifyReason reason) {
        this.setWidth(width, type, reason);
        this.setHeight(height, type, reason);
    }
    
    default void setWidth(final float width, final BoundsType type, final ModifyReason reason) {
        this.setWidth(width - this.getOffset(type, OffsetSide.LEFT) - this.getOffset(type, OffsetSide.RIGHT), reason);
    }
    
    default void setHeight(final float height, final BoundsType type, final ModifyReason reason) {
        this.setHeight(height - this.getOffset(type, OffsetSide.TOP) - this.getOffset(type, OffsetSide.BOTTOM), reason);
    }
    
    default float getX(final BoundsType type) {
        return this.getLeft(type);
    }
    
    default float getY(final BoundsType type) {
        return this.getTop(type);
    }
    
    default float getWidth(final BoundsType type) {
        return this.getRight(type) - this.getLeft(type);
    }
    
    default float getHeight(final BoundsType type) {
        return this.getBottom(type) - this.getTop(type);
    }
    
    default float getLeft(final BoundsType type) {
        return this.getLeft() - this.getOffset(type, OffsetSide.LEFT);
    }
    
    default float getTop(final BoundsType type) {
        return this.getTop() - this.getOffset(type, OffsetSide.TOP);
    }
    
    default float getRight(final BoundsType type) {
        return this.getRight() + this.getOffset(type, OffsetSide.RIGHT);
    }
    
    default float getBottom(final BoundsType type) {
        return this.getBottom() + this.getOffset(type, OffsetSide.BOTTOM);
    }
    
    default float getMaxX(final BoundsType type) {
        return this.getRight(type);
    }
    
    default float getMaxY(final BoundsType type) {
        return this.getBottom(type);
    }
    
    default float getCenterX(final BoundsType type) {
        return this.getLeft(type) + this.getWidth(type) / 2.0f;
    }
    
    default float getCenterY(final BoundsType type) {
        return this.getTop(type) + this.getHeight(type) / 2.0f;
    }
    
    default float getOffset(final BoundsType type, final RectangleState state) {
        switch (state) {
            case LEFT: {
                return this.getOffset(type, OffsetSide.LEFT);
            }
            case TOP: {
                return this.getOffset(type, OffsetSide.TOP);
            }
            case BOTTOM: {
                return this.getOffset(type, OffsetSide.BOTTOM);
            }
            case RIGHT: {
                return this.getOffset(type, OffsetSide.RIGHT);
            }
            case WIDTH: {
                return this.getHorizontalOffset(type);
            }
            case HEIGHT: {
                return this.getVerticalOffset(type);
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(state));
            }
        }
    }
    
    default float getHorizontalOffset(final BoundsType type) {
        return this.getOffset(type, OffsetSide.LEFT) + this.getOffset(type, OffsetSide.RIGHT);
    }
    
    default float getVerticalOffset(final BoundsType type) {
        return this.getOffset(type, OffsetSide.TOP) + this.getOffset(type, OffsetSide.BOTTOM);
    }
    
    default boolean isInRectangle(final BoundsType type, final Point point) {
        return this.isInRectangle(type, (float)point.getX(), (float)point.getY());
    }
    
    default boolean isInRectangle(final BoundsType type, final float x, final float y) {
        return this.isXInRectangle(type, x) && this.isYInRectangle(type, y);
    }
    
    default boolean isXInRectangle(final BoundsType type, final float x) {
        return x >= this.getLeft(type) && x <= this.getRight(type);
    }
    
    default boolean isYInRectangle(final BoundsType type, final float y) {
        return y >= this.getTop(type) && y <= this.getBottom(type);
    }
    
    default MutableRectangle copy(final BoundsType type, final MutableRectangle destination) {
        destination.setBounds(this.getLeft(type), this.getTop(type), this.getRight(type), this.getBottom(type));
        return destination;
    }
    
    default MutableRectangle copy(final BoundsType type) {
        return this.copy(type, new DefaultRectangle());
    }
}
