// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.NotNull;

public interface MutableRectangle extends Rectangle
{
    default void setBounds(final float left, final float top, final float right, final float bottom) {
        this.setLeft(left);
        this.setTop(top);
        this.setRight(right);
        this.setBottom(bottom);
    }
    
    default void setSize(final float size) {
        this.setSize(size, size);
    }
    
    default void setSize(final float width, final float height) {
        this.setWidth(width);
        this.setHeight(height);
    }
    
    default void setPosition(final Point position) {
        this.setPosition((float)position.getX(), (float)position.getY());
    }
    
    default void setPosition(final float x, final float y) {
        this.setX(x);
        this.setY(y);
    }
    
    default void setX(final float x) {
        this.setRight(x + this.getWidth());
        this.setLeft(x);
    }
    
    default void setY(final float y) {
        this.setBottom(y + this.getHeight());
        this.setTop(y);
    }
    
    default void setRightX(final float rightX) {
        this.setLeft(rightX - this.getWidth());
        this.setRight(rightX);
    }
    
    default void setBottomY(final float bottomY) {
        this.setTop(bottomY - this.getHeight());
        this.setBottom(bottomY);
    }
    
    default void setWidth(final float width) {
        this.setRight(this.getLeft() + width);
    }
    
    default void setLeftWidth(final float width) {
        this.setLeft(this.getRight() - width);
    }
    
    default void setHeight(final float height) {
        this.setBottom(this.getTop() + height);
    }
    
    default void setTopHeight(final float height) {
        this.setTop(this.getBottom() - height);
    }
    
    void setLeft(final float p0);
    
    void setTop(final float p0);
    
    void setRight(final float p0);
    
    void setBottom(final float p0);
    
    default void crop(@NotNull final Rectangle rectangle) {
        this.setLeft(Math.max(this.getLeft(), rectangle.getLeft()));
        this.setTop(Math.max(this.getTop(), rectangle.getTop()));
        this.setRight(Math.min(this.getRight(), rectangle.getRight()));
        this.setBottom(Math.min(this.getBottom(), rectangle.getBottom()));
    }
    
    default void extend(@NotNull final Rectangle rectangle) {
        this.setLeft(Math.min(this.getLeft(), rectangle.getLeft()));
        this.setTop(Math.min(this.getTop(), rectangle.getTop()));
        this.setRight(Math.max(this.getRight(), rectangle.getRight()));
        this.setBottom(Math.max(this.getBottom(), rectangle.getBottom()));
    }
    
    default void set(@NotNull final Rectangle rectangle) {
        this.setBounds(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom());
    }
    
    @NotNull
    default MutableRectangle shift(final float x, final float y) {
        this.setLeft(this.getLeft() + x);
        this.setTop(this.getTop() + y);
        this.setRight(this.getRight() + x);
        this.setBottom(this.getBottom() + y);
        return this;
    }
    
    @NotNull
    default MutableRectangle scale(final float x, final float y) {
        this.setWidth(this.getWidth() * x);
        this.setHeight(this.getHeight() * y);
        return this;
    }
    
    @NotNull
    default MutableRectangle expand(final float padding) {
        this.setBounds(this.getLeft() - padding, this.getTop() - padding, this.getRight() + padding, this.getBottom() + padding);
        return this;
    }
    
    @NotNull
    default MutableRectangle expand(final float paddingX, final float paddingY) {
        this.setBounds(this.getLeft() - paddingX, this.getTop() - paddingY, this.getRight() + paddingX, this.getBottom() + paddingY);
        return this;
    }
    
    @NotNull
    default MutableRectangle expand(@NotNull final FloatVector2 padding) {
        return this.expand(padding.getX(), padding.getY());
    }
    
    @NotNull
    default MutableRectangle shrink(final float padding) {
        return this.expand(-padding);
    }
    
    default void apply(final Stack stack) {
        final float left = this.getLeft();
        final float top = this.getTop();
        final float right = this.getRight();
        final float bottom = this.getBottom();
        final FloatMatrix4 matrix = stack.getProvider().getPosition();
        this.setLeft(matrix.transformVectorX(left, top, 0.0f));
        this.setTop(matrix.transformVectorY(left, top, 0.0f));
        this.setRight(matrix.transformVectorX(right, bottom, 0.0f));
        this.setBottom(matrix.transformVectorY(right, bottom, 0.0f));
    }
    
    default void add(final float x, final float y) {
        this.setX(this.getLeft() + x);
        this.setY(this.getTop() + y);
    }
    
    default void add(final Rectangle rectangle) {
        this.setX(this.getLeft() + rectangle.getLeft());
        this.setY(this.getTop() + rectangle.getTop());
    }
    
    default void subtract(final Rectangle rectangle) {
        this.setX(this.getLeft() - rectangle.getLeft());
        this.setY(this.getTop() - rectangle.getTop());
    }
    
    default void shiftToBounds(final Rectangle boundaries) {
        if (this.getRight() > boundaries.getRight()) {
            this.setX(boundaries.getRight() - this.getWidth());
        }
        if (this.getBottom() > boundaries.getBottom()) {
            this.setY(boundaries.getBottom() - this.getHeight());
        }
        if (this.getLeft() < boundaries.getLeft()) {
            this.setX(boundaries.getLeft());
        }
        if (this.getTop() < boundaries.getTop()) {
            this.setY(boundaries.getTop());
        }
    }
}
