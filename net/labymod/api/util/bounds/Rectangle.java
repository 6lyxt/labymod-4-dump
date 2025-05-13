// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public interface Rectangle
{
    default float getX() {
        return this.getLeft();
    }
    
    default float getY() {
        return this.getTop();
    }
    
    default float getWidth() {
        return this.getRight() - this.getLeft();
    }
    
    default float getHeight() {
        return this.getBottom() - this.getTop();
    }
    
    default float getMaxX() {
        return this.getRight();
    }
    
    default float getMaxY() {
        return this.getBottom();
    }
    
    default float getCenterX() {
        return this.getLeft() + this.getWidth() / 2.0f;
    }
    
    default float getCenterY() {
        return this.getTop() + this.getHeight() / 2.0f;
    }
    
    float getLeft();
    
    float getTop();
    
    float getRight();
    
    float getBottom();
    
    @NotNull
    default MutableRectangle copy() {
        return new DefaultRectangle(this);
    }
    
    default boolean isOverlapping(@NotNull final Rectangle r) {
        return this.isOverlapping(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    
    default boolean isOverlapping(final float x, final float y, final float width, final float height) {
        return this.isOverlappingHorizontally(x, width) && this.isOverlappingVertically(y, height);
    }
    
    default boolean isOverlappingBy(@NotNull final Rectangle r, final float percentage) {
        return this.isOverlappingBy(r.getX(), r.getY(), r.getWidth(), r.getHeight(), percentage);
    }
    
    default boolean isOverlappingBy(final float x, final float y, final float width, final float height, final float percentage) {
        final float hor = this.getOverlappingPercentageHorizontally(x, width);
        final float ver = this.getOverlappingPercentageVertically(y, height);
        return hor * ver >= percentage;
    }
    
    default boolean isOverlappingVertically(@NotNull final Rectangle r) {
        return this.isOverlappingVertically(r.getY(), r.getHeight());
    }
    
    default boolean isOverlappingVertically(final float y, final float height) {
        return this.getTop() < y + height && this.getY() + this.getHeight() >= y;
    }
    
    default boolean isOverlappingVerticallyBy(@NotNull final Rectangle r, final float percentage) {
        return this.isOverlappingVerticallyBy(r.getY(), r.getHeight(), percentage);
    }
    
    default boolean isOverlappingVerticallyBy(final float y, final float height, final float percentage) {
        return this.getOverlappingPercentageVertically(y, height) >= percentage;
    }
    
    default float getOverlappingPercentageVertically(@NotNull final Rectangle r) {
        return this.getOverlappingPercentageVertically(r.getY(), r.getHeight());
    }
    
    default float getOverlappingPercentageVertically(final float y, final float height) {
        final float top = y;
        final float bottom = y + height;
        final float overlappingHeight = Math.min(this.getBottom() - top, bottom - this.getTop());
        return overlappingHeight / this.getHeight();
    }
    
    default boolean isOverlappingHorizontally(@NotNull final Rectangle r) {
        return this.isOverlappingHorizontally(r.getX(), r.getWidth());
    }
    
    default boolean isOverlappingHorizontally(final float x, final float width) {
        return this.getLeft() < x + width && this.getLeft() + this.getWidth() >= x;
    }
    
    default boolean isOverlappingHorizontallyBy(@NotNull final Rectangle r, final float percentage) {
        return this.isOverlappingHorizontallyBy(r.getX(), r.getWidth(), percentage);
    }
    
    default boolean isOverlappingHorizontallyBy(final float x, final float width, final float percentage) {
        return this.getOverlappingPercentageHorizontally(x, width) >= percentage;
    }
    
    default float getOverlappingPercentageHorizontally(@NotNull final Rectangle r) {
        return this.getOverlappingPercentageHorizontally(r.getX(), r.getWidth());
    }
    
    default float getOverlappingPercentageHorizontally(final float x, final float width) {
        final float left = x;
        final float right = x + width;
        final float overlappingWidth = Math.min(this.getRight() - left, right - this.getLeft());
        return overlappingWidth / this.getWidth();
    }
    
    default boolean isInRectangle(final Rectangle rectangle) {
        return this.isInRectangle(rectangle.getX(), rectangle.getY()) && this.isInRectangle(rectangle.getRight(), rectangle.getBottom());
    }
    
    default boolean isInRectangle(final float x, final float y) {
        return this.isXInRectangle(x) && this.isYInRectangle(y);
    }
    
    default boolean isInRectangle(final Point point) {
        return this.isInRectangle((float)point.getX(), (float)point.getY());
    }
    
    default boolean isInRelativeRectangle(final float x, final float y) {
        return x >= this.getX() && x < this.getX() + this.getWidth() && y >= this.getY() && y < this.getY() + this.getHeight();
    }
    
    default boolean isInRelativeRectangle(final Point point) {
        return this.isInRelativeRectangle((float)point.getX(), (float)point.getY());
    }
    
    default boolean isXInRectangle(final float x) {
        return x >= this.getLeft() && x < this.getRight();
    }
    
    default boolean isYInRectangle(final float y) {
        return y >= this.getTop() && y < this.getBottom();
    }
    
    default boolean isInverted() {
        return this.getWidth() < 0.0f || this.getHeight() < 0.0f;
    }
    
    default boolean isRoundedPositionEqual(@NotNull final Rectangle other) {
        float ox = other.getX();
        float oy = other.getY();
        float tx = this.getX();
        float ty = this.getY();
        ox = (float)Math.round(ox);
        oy = (float)Math.round(oy);
        tx = (float)Math.round(tx);
        ty = (float)Math.round(ty);
        return ox == tx && oy == ty;
    }
    
    default boolean isRoundedSizeEqual(@NotNull final Rectangle other) {
        float ow = other.getWidth();
        float oh = other.getHeight();
        float tw = this.getWidth();
        float th = this.getHeight();
        ow = (float)Math.round(ow);
        oh = (float)Math.round(oh);
        tw = (float)Math.round(tw);
        th = (float)Math.round(th);
        return ow == tw && oh == th;
    }
    
    default boolean equalsBounds(@NotNull final Rectangle other) {
        return Math.abs(this.getLeft() - other.getLeft()) < 0.001f && Math.abs(this.getTop() - other.getTop()) < 0.001f && Math.abs(this.getRight() - other.getRight()) < 0.001f && Math.abs(this.getBottom() - other.getBottom()) < 0.001f;
    }
    
    default float distanceSquared(@NotNull final Rectangle other) {
        if (other.isOverlapping(this)) {
            return 0.0f;
        }
        float dx = 0.0f;
        float dy = 0.0f;
        if (this.getLeft() > other.getRight()) {
            dx = this.getLeft() - other.getRight();
        }
        else if (this.getRight() < other.getLeft()) {
            dx = other.getLeft() - this.getRight();
        }
        if (this.getTop() > other.getBottom()) {
            dy = this.getTop() - other.getBottom();
        }
        else if (this.getBottom() < other.getTop()) {
            dy = other.getTop() - this.getBottom();
        }
        return dx * dx + dy * dy;
    }
    
    default float distanceSquared(@NotNull final Point point) {
        return this.distanceSquared((float)point.getX(), (float)point.getY());
    }
    
    default float distanceSquared(final float x, final float y) {
        return MathHelper.square(this.getCenterX() - x) + MathHelper.square(this.getCenterY() - y);
    }
    
    default Rectangle lerp(final Rectangle destination, final float progress) {
        final float left = MathHelper.lerp(destination.getLeft(), this.getLeft(), progress);
        final float top = MathHelper.lerp(destination.getTop(), this.getTop(), progress);
        final float right = MathHelper.lerp(destination.getRight(), this.getRight(), progress);
        final float bottom = MathHelper.lerp(destination.getBottom(), this.getBottom(), progress);
        return new DefaultRectangle(left, top, right, bottom);
    }
    
    default boolean hasSize() {
        return this.getWidth() > 0.0f && this.getHeight() > 0.0f;
    }
    
    @Nullable
    default Rectangle intersection(final Rectangle other) {
        final float left = Math.max(this.getLeft(), other.getLeft());
        final float top = Math.max(this.getTop(), other.getTop());
        final float right = Math.min(this.getRight(), other.getRight());
        final float bottom = Math.min(this.getBottom(), other.getBottom());
        return (left < right && top < bottom) ? relative(left, top, right - left, bottom - top) : null;
    }
    
    default MutableRectangle absolute(final float left, final float top, final float right, final float bottom) {
        return new DefaultRectangle(left, top, right, bottom);
    }
    
    default MutableRectangle relative(final float x, final float y, final float width, final float height) {
        return new DefaultRectangle(x, y, x + width, y + height);
    }
    
    default MutableRectangle extendable() {
        return new DefaultRectangle(Float.MAX_VALUE, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
    }
}
