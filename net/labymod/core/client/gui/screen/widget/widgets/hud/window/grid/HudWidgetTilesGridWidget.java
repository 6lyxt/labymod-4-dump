// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class HudWidgetTilesGridWidget extends AbstractWidget<HudWidgetTypeInfoWidget>
{
    private static final ModifyReason HUD_WIDGET_DRAGGING;
    private static final ModifyReason GRID_UPDATE;
    private final WidgetsEditorActivity editor;
    private final LssProperty<Float> spaceBetweenEntries;
    private HudWidgetTypeInfoWidget draggingWidget;
    private float dragOffsetX;
    private float dragOffsetY;
    private MutableRectangle fallbackRectangle;
    
    public HudWidgetTilesGridWidget(final WidgetsEditorActivity editor) {
        this.spaceBetweenEntries = new LssProperty<Float>(1.0f);
        this.editor = editor;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.updateTiles();
    }
    
    public void updateTiles() {
        final Bounds bounds = this.bounds();
        final float space = this.spaceBetweenEntries.get();
        final int tilesPerRow = Math.max(MathHelper.floor(bounds.getWidth() / 80.0f), 1);
        final float tileWidth = (bounds.getWidth() - space * (tilesPerRow - 1)) / tilesPerRow;
        float x = bounds.getLeft();
        float y = bounds.getTop();
        float maxHeight = 0.0f;
        for (final HudWidgetTypeInfoWidget child : this.children) {
            final Bounds childBounds = child.bounds();
            childBounds.setX(x, HudWidgetTilesGridWidget.GRID_UPDATE);
            childBounds.setY(y, HudWidgetTilesGridWidget.GRID_UPDATE);
            childBounds.setWidth(tileWidth, HudWidgetTilesGridWidget.GRID_UPDATE);
            maxHeight = Math.max(maxHeight, childBounds.getHeight());
            x += tileWidth + space;
            if (x >= bounds.getRight()) {
                x = bounds.getLeft();
                y += maxHeight + space;
                maxHeight = 0.0f;
            }
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            final HudWidgetTypeInfoWidget widget = this.getTargetAt((float)mouse.getX(), (float)mouse.getY());
            if (widget != null && !widget.hudWidget().isEnabled()) {
                this.draggingWidget = widget;
                final Bounds bounds = widget.bounds();
                this.dragOffsetX = mouse.getX() - bounds.getX();
                this.dragOffsetY = mouse.getY() - bounds.getY();
                this.fallbackRectangle = bounds.copy();
                try {
                    this.editor.renderer().createHudWidget(widget.hudWidget());
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
                widget.draggable().set(true);
                widget.setDragging(true);
                return false;
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (this.draggingWidget != null) {
            final double x = mouse.getXDouble() - this.dragOffsetX;
            final double y = mouse.getYDouble() - this.dragOffsetY;
            this.draggingWidget.bounds().setPosition((float)x, (float)y, HudWidgetTilesGridWidget.HUD_WIDGET_DRAGGING);
            return false;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            final HudWidgetTypeInfoWidget widget = this.draggingWidget;
            if (widget != null) {
                this.draggingWidget = null;
                widget.priorityLayer().set(PriorityLayer.NORMAL);
                widget.bounds().set(this.fallbackRectangle, HudWidgetTilesGridWidget.HUD_WIDGET_DRAGGING);
                if (widget.hudWidget().isEnabled()) {
                    widget.skipInterpolation();
                    widget.reInitialize();
                }
                widget.setDragging(false);
                widget.draggable().set(false);
                this.updateTiles();
                return false;
            }
        }
        return super.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final Bounds bounds = this.bounds();
        final float space = this.spaceBetweenEntries.get();
        final int tilesPerRow = Math.max(MathHelper.floor(bounds.getWidth() / 80.0f), 1);
        float height = 0.0f;
        float maxHeight = 0.0f;
        for (int i = 0; i < this.children.size(); ++i) {
            final Widget child = this.children.get(i);
            maxHeight = Math.max(maxHeight, child.bounds().getHeight());
            if (i % tilesPerRow == 0) {
                height += maxHeight + space;
                maxHeight = 0.0f;
            }
        }
        return height;
    }
    
    private HudWidgetTypeInfoWidget getTargetAt(final float x, final float y) {
        final Iterator<HudWidgetTypeInfoWidget> iterator = this.getChildrenAt((int)x, (int)y).iterator();
        if (iterator.hasNext()) {
            final HudWidgetTypeInfoWidget widget = iterator.next();
            return widget;
        }
        return null;
    }
    
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    public HudWidgetTypeInfoWidget draggingWidget() {
        return this.draggingWidget;
    }
    
    public WidgetsEditorActivity editor() {
        return this.editor;
    }
    
    static {
        HUD_WIDGET_DRAGGING = ModifyReason.of("hudWidgetDragging");
        GRID_UPDATE = ModifyReason.of("gridUpdate");
    }
}
