// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

import java.util.Map;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.NotNull;

public interface ReasonableMutableRectangle extends Rectangle
{
    default void setBounds(final float left, final float top, final float right, final float bottom, final ModifyReason reason) {
        this.setLeft(left, reason);
        this.setTop(top, reason);
        this.setRight(right, reason);
        this.setBottom(bottom, reason);
    }
    
    default void setSize(final float size, final ModifyReason reason) {
        this.setSize(size, size, reason);
    }
    
    default void setSize(final float width, final float height, final ModifyReason reason) {
        this.setWidth(width, reason);
        this.setHeight(height, reason);
    }
    
    default void setPosition(final float x, final float y, final ModifyReason reason) {
        this.setX(x, reason);
        this.setY(y, reason);
    }
    
    default void setX(final float x, final ModifyReason reason) {
        this.setRight(x + this.getWidth(), reason);
        this.setLeft(x, reason);
    }
    
    default void setY(final float y, final ModifyReason reason) {
        this.setBottom(y + this.getHeight(), reason);
        this.setTop(y, reason);
    }
    
    default void setRightX(final float rightX, final ModifyReason reason) {
        this.setLeft(rightX - this.getWidth(), reason);
        this.setRight(rightX, reason);
    }
    
    default void setBottomY(final float bottomY, final ModifyReason reason) {
        this.setTop(bottomY - this.getHeight(), reason);
        this.setBottom(bottomY, reason);
    }
    
    default void setWidth(final float width, final ModifyReason reason) {
        this.setRight(this.getLeft() + width, reason);
    }
    
    default void setLeftWidth(final float width, final ModifyReason reason) {
        this.setLeft(this.getRight() - width, reason);
    }
    
    default void setHeight(final float height, final ModifyReason reason) {
        this.setBottom(this.getTop() + height, reason);
    }
    
    default void setTopHeight(final float height, final ModifyReason reason) {
        this.setTop(this.getBottom() - height, reason);
    }
    
    void setLeft(final float p0, final ModifyReason p1);
    
    void setTop(final float p0, final ModifyReason p1);
    
    void setRight(final float p0, final ModifyReason p1);
    
    void setBottom(final float p0, final ModifyReason p1);
    
    default void crop(@NotNull final Rectangle rectangle, final ModifyReason reason) {
        this.setLeft(Math.max(this.getLeft(), rectangle.getLeft()), reason);
        this.setTop(Math.max(this.getTop(), rectangle.getTop()), reason);
        this.setRight(Math.min(this.getRight(), rectangle.getRight()), reason);
        this.setBottom(Math.min(this.getBottom(), rectangle.getBottom()), reason);
    }
    
    default void extend(@NotNull final Rectangle rectangle, final ModifyReason reason) {
        this.setLeft(Math.min(this.getLeft(), rectangle.getLeft()), reason);
        this.setTop(Math.min(this.getTop(), rectangle.getTop()), reason);
        this.setRight(Math.max(this.getRight(), rectangle.getRight()), reason);
        this.setBottom(Math.max(this.getBottom(), rectangle.getBottom()), reason);
    }
    
    default void set(@NotNull final Rectangle rectangle, final ModifyReason reason) {
        this.setBounds(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), reason);
    }
    
    @NotNull
    default ReasonableMutableRectangle shift(final float x, final float y, final ModifyReason reason) {
        this.setLeft(this.getLeft() + x, reason);
        this.setTop(this.getTop() + y, reason);
        this.setRight(this.getRight() + x, reason);
        this.setBottom(this.getBottom() + y, reason);
        return this;
    }
    
    @NotNull
    default ReasonableMutableRectangle expand(final float padding, final ModifyReason reason) {
        this.setBounds(this.getLeft() - padding, this.getTop() - padding, this.getRight() + padding, this.getBottom() + padding, reason);
        return this;
    }
    
    @NotNull
    default ReasonableMutableRectangle expand(final float paddingX, final float paddingY, final ModifyReason reason) {
        this.setBounds(this.getLeft() - paddingX, this.getTop() - paddingY, this.getRight() + paddingX, this.getBottom() + paddingY, reason);
        return this;
    }
    
    @NotNull
    default ReasonableMutableRectangle expand(@NotNull final FloatVector2 padding, final ModifyReason reason) {
        return this.expand(padding.getX(), padding.getY(), reason);
    }
    
    @NotNull
    default ReasonableMutableRectangle shrink(final float padding, final ModifyReason reason) {
        return this.expand(-padding, reason);
    }
    
    default void apply(final Stack stack, final ModifyReason reason) {
        final float left = this.getLeft();
        final float top = this.getTop();
        final float right = this.getRight();
        final float bottom = this.getBottom();
        final FloatMatrix4 matrix = stack.getProvider().getPosition();
        this.setLeft(matrix.transformVectorX(left, top, 0.0f), reason);
        this.setTop(matrix.transformVectorY(left, top, 0.0f), reason);
        this.setRight(matrix.transformVectorX(right, bottom, 0.0f), reason);
        this.setBottom(matrix.transformVectorY(right, bottom, 0.0f), reason);
    }
    
    default void add(final float x, final float y, final ModifyReason reason) {
        this.setX(this.getLeft() + x, reason);
        this.setY(this.getTop() + y, reason);
    }
    
    default void add(final Rectangle rectangle, final ModifyReason reason) {
        this.setX(this.getLeft() + rectangle.getLeft(), reason);
        this.setY(this.getTop() + rectangle.getTop(), reason);
    }
    
    default void subtract(final Rectangle rectangle, final ModifyReason reason) {
        this.setX(this.getLeft() - rectangle.getLeft(), reason);
        this.setY(this.getTop() - rectangle.getTop(), reason);
    }
    
    default void shiftToBounds(final Rectangle boundaries, final ModifyReason reason, final boolean outer) {
        if (this.getRight() > boundaries.getRight()) {
            if (outer) {
                this.setRight(boundaries.getRight(), reason);
            }
            else {
                this.setX(boundaries.getRight() - this.getWidth(), reason);
            }
        }
        if (this.getBottom() > boundaries.getBottom()) {
            if (outer) {
                this.setBottom(boundaries.getBottom(), reason);
            }
            else {
                this.setY(boundaries.getBottom() - this.getHeight(), reason);
            }
        }
        if (this.getLeft() < boundaries.getLeft()) {
            this.setX(boundaries.getLeft(), reason);
        }
        if (this.getTop() < boundaries.getTop()) {
            this.setY(boundaries.getTop(), reason);
        }
    }
    
    void recordModifications();
    
    void stopRecordingModifications();
    
    @NotNull
    Map<RectangleState, RectangleModification> lastModifications();
}
