// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.transform;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class InterpolateWidget extends SimpleWidget
{
    private float interpolateDuration;
    private float prevX;
    private float prevY;
    private long timePositionAnimationStart;
    private long timeLastInterpolationSkipped;
    
    public InterpolateWidget(final float interpolateDuration) {
        this.interpolateDuration = interpolateDuration;
        this.skipInterpolation();
    }
    
    public InterpolateWidget() {
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.skipInterpolation();
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.interpolateDuration == 0.0f) {
            super.render(context);
            return;
        }
        final long duration = TimeUtil.getMillis() - this.timePositionAnimationStart;
        final float progress = Math.min(1.0f / this.interpolateDuration * duration, 1.0f);
        final boolean hasAnimation = this.prevX != 0.0f || this.prevY != 0.0f;
        final Bounds bounds = this.bounds();
        final float x = MathHelper.lerp(bounds.getX(), this.prevX, hasAnimation ? progress : 1.0f);
        final float y = MathHelper.lerp(bounds.getY(), this.prevY, hasAnimation ? progress : 1.0f);
        final Stack stack = context.stack();
        try {
            stack.push();
            stack.translate(x - bounds.getX(), y - bounds.getY(), 0.0f);
            super.render(context);
        }
        finally {
            stack.pop();
        }
        if (hasAnimation) {
            this.prevX = x;
            this.prevY = y;
            this.timePositionAnimationStart = TimeUtil.getMillis();
        }
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        final boolean isInitializing = this.timeLastInterpolationSkipped + 200L > TimeUtil.getMillis();
        if (this.interpolateDuration == 0.0f || isInitializing) {
            return;
        }
        final long duration = TimeUtil.getMillis() - this.timePositionAnimationStart;
        final float progress = Math.min(1.0f / this.interpolateDuration * duration, 1.0f);
        final boolean hasAnimation = this.prevX != 0.0f || this.prevY != 0.0f;
        this.prevX = MathHelper.lerp(previousRect.getX(), this.prevX, hasAnimation ? progress : 1.0f);
        this.prevY = MathHelper.lerp(previousRect.getY(), this.prevY, hasAnimation ? progress : 1.0f);
        this.timePositionAnimationStart = TimeUtil.getMillis();
    }
    
    public float getInterpolationOffsetX() {
        final boolean hasAnimation = this.prevX != 0.0f || this.prevY != 0.0f;
        return hasAnimation ? (this.prevX - this.bounds().getX()) : 0.0f;
    }
    
    public float getInterpolationOffsetY() {
        final boolean hasAnimation = this.prevX != 0.0f || this.prevY != 0.0f;
        return hasAnimation ? (this.prevY - this.bounds().getY()) : 0.0f;
    }
    
    public void skipInterpolation() {
        final Bounds bounds = this.bounds();
        this.prevX = bounds.getX();
        this.prevY = bounds.getY();
        this.timeLastInterpolationSkipped = TimeUtil.getMillis();
    }
    
    public void setInterpolateDuration(final float interpolateDuration) {
        this.interpolateDuration = interpolateDuration;
    }
    
    public void disableInterpolation() {
        this.interpolateDuration = 0.0f;
    }
}
