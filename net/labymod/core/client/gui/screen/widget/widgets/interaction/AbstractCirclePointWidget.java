// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.interaction;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class AbstractCirclePointWidget<T extends AbstractPointWidget> extends AbstractWidget<T>
{
    protected final InteractionAnimationController animationController;
    private boolean hasLastMousePosition;
    private int lastMouseX;
    private int lastMouseY;
    
    public AbstractCirclePointWidget(final InteractionAnimationController animationController) {
        this.animationController = animationController;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final MutableMouse mouse = context.mouse();
        this.lastMouseX = mouse.getX();
        this.lastMouseY = mouse.getY();
        this.hasLastMousePosition = true;
    }
    
    @Override
    public void tick() {
        super.tick();
        final AbstractPointWidget nearest = this.getNearestPoint();
        for (final AbstractPointWidget child : this.children) {
            child.updateHovered(child == nearest);
        }
    }
    
    public T getNearestPoint() {
        if (!this.hasLastMousePosition) {
            return null;
        }
        final Bounds rendererBounds = this.bounds();
        final float rendererCenterX = rendererBounds.getCenterX();
        final float rendererCenterY = rendererBounds.getCenterY();
        T nearest = null;
        float nearestDistance = Float.MAX_VALUE;
        final float minRadius = this.getMinRadius();
        for (final T widget : this.children) {
            final Bounds bounds = widget.bounds();
            final float widgetCenterX = bounds.getCenterX();
            final float widgetCenterY = bounds.getCenterY();
            final float distanceToWidget = MathHelper.square(this.lastMouseX - widgetCenterX) + MathHelper.square(this.lastMouseY - widgetCenterY);
            final float distanceToCenter = MathHelper.square(widgetCenterX - rendererCenterX) + MathHelper.square(widgetCenterY - rendererCenterY);
            if (distanceToWidget < nearestDistance && distanceToCenter > MathHelper.square(minRadius) && distanceToWidget < MathHelper.square(widget.getRadius())) {
                nearest = widget;
                nearestDistance = distanceToWidget;
            }
        }
        return nearest;
    }
    
    protected float getRadius() {
        return this.bounds().getHeight() / 2.0f;
    }
    
    protected float getMinRadius() {
        return this.getRadius() / 1.5f;
    }
}
