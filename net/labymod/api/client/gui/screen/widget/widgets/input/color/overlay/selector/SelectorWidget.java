// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public abstract class SelectorWidget extends AbstractWidget<Widget>
{
    protected static final RectangleRenderContext RENDER_CONTEXT;
    private final ColorData colorData;
    private final MarkerWidget markerWidget;
    private final LssProperty<Orientation> orientation;
    private boolean dragging;
    
    protected SelectorWidget(final ColorData colorData) {
        this.orientation = new LssProperty<Orientation>(Orientation.NONE);
        this.dragging = false;
        this.colorData = colorData;
        this.markerWidget = new MarkerWidget(this);
        colorData.addUpdateListener(this, () -> {
            if (!this.dragging) {
                this.updateMarkerPosition();
            }
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<MarkerWidget>)this).addChild(this.markerWidget);
        this.updateMarkerPosition();
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final Orientation orientation = this.orientation.get();
        if (orientation == Orientation.NONE) {
            return;
        }
        if (this.dragging) {
            final Bounds bounds = this.bounds();
            this.update(Math.min(Math.max(0.0f, (float)(context.mouse().getXDouble() - bounds.getLeft())), bounds.getWidth()), Math.min(Math.max(0.0f, (float)(-(context.mouse().getYDouble() - bounds.getBottom()))), bounds.getHeight()));
        }
        this.renderSelector(orientation, context.stack());
        if (this.markerWidget != null) {
            this.markerWidget.render(context);
        }
        if (this.dragging && !this.labyAPI.minecraft().isMouseDown(MouseButton.LEFT)) {
            this.dragging = false;
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton != MouseButton.LEFT) {
            return false;
        }
        this.dragging = true;
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.dragging) {
            this.dragging = false;
            return true;
        }
        return super.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (this.markerWidget != null) {
            this.markerWidget.reapply();
        }
    }
    
    public abstract void renderSelector(final Orientation p0, final Stack p1);
    
    public abstract void update(final float p0, final float p1);
    
    public abstract void updateMarkerPosition();
    
    public void updateMarkerPosition(final float posX, final float posY) {
        if (posX >= 0.0f && posX <= this.bounds().getWidth(BoundsType.INNER)) {
            this.markerWidget().setX(posX);
        }
        if (posY >= 0.0f && posY <= this.bounds().getHeight(BoundsType.INNER)) {
            this.markerWidget().setY(posY);
        }
        this.markerWidget().bounds().checkForChanges();
    }
    
    public ColorData colorData() {
        return this.colorData;
    }
    
    public MarkerWidget markerWidget() {
        return this.markerWidget;
    }
    
    public LssProperty<Orientation> orientation() {
        return this.orientation;
    }
    
    static {
        RENDER_CONTEXT = Laby.references().rectangleRenderContext();
    }
}
