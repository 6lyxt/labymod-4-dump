// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ScrollbarWidget extends SimpleWidget
{
    private final ScrollWidget scrollWidget;
    private final LssProperty<Float> scrollButtonClickOffset;
    private final LssProperty<Boolean> useLssPosition;
    private final LssProperty<Integer> scrollColor;
    private final LssProperty<Integer> minScrollHeight;
    private final LssProperty<Integer> scrollHoverColor;
    private final LssProperty<Integer> scrollBackgroundColor;
    
    public ScrollbarWidget(final ScrollWidget scrollWidget) {
        this.scrollButtonClickOffset = new LssProperty<Float>(0.0f);
        this.useLssPosition = new LssProperty<Boolean>(false);
        this.scrollColor = new LssProperty<Integer>(ColorFormat.ARGB32.pack(0, 0, 0, 200));
        this.minScrollHeight = new LssProperty<Integer>(1);
        this.scrollHoverColor = new LssProperty<Integer>(ColorFormat.ARGB32.pack(30, 30, 30, 200));
        this.scrollBackgroundColor = new LssProperty<Integer>(ColorFormat.ARGB32.pack(110, 110, 110, 100));
        this.scrollWidget = scrollWidget;
        this.draggable().set(true);
    }
    
    @Override
    public String getDefaultRendererName() {
        return "Scrollbar";
    }
    
    public ScrollWidget scrollWidget() {
        return this.scrollWidget;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isHovered()) {
            final float buttonOffset = (float)(mouse.getYDouble() - (this.bounds().getTop(BoundsType.MIDDLE) + this.getScrollButtonOffset()));
            if (buttonOffset >= 0.0f || buttonOffset <= this.getScrollButtonHeight()) {
                this.setDragging(true);
                this.scrollButtonClickOffset.set(buttonOffset);
                return true;
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (this.isDragging()) {
            final float offset = mouse.getY() - this.bounds().getTop(BoundsType.MIDDLE) - this.scrollButtonClickOffset.get();
            final float scale = this.getScrollbarHeight() / this.getContentHeight();
            final float scrollPositionY = offset / scale;
            final float previousPosition = this.scrollWidget.session().getScrollPositionY();
            this.scrollWidget.session().setScrollPositionY(scrollPositionY);
            this.scrollWidget.updateScrollbarBounds();
            this.scrollWidget.scrolled(previousPosition, this.scrollWidget.session().getScrollPositionY());
            return true;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        this.setDragging(false);
        return super.mouseReleased(mouse, mouseButton);
    }
    
    public float getScrollButtonHeight() {
        final float contentHeight = this.getContentHeight();
        final float scrollbarHeight = this.bounds().getHeight(BoundsType.MIDDLE);
        return Math.max(this.minScrollHeight.get(), scrollbarHeight / contentHeight * scrollbarHeight);
    }
    
    public float getScrollButtonOffset() {
        final float percentage = 1.0f / this.scrollWidget.getOverflowHeight() * this.getScrollPositionY();
        final float maxOffset = this.getScrollbarHeight() - this.getScrollButtonHeight();
        return maxOffset * percentage;
    }
    
    protected float getContentHeight() {
        return this.scrollWidget.session().getContentHeight();
    }
    
    private float getScrollbarHeight() {
        return this.bounds().getHeight(BoundsType.MIDDLE);
    }
    
    private float getScrollPositionY() {
        return this.scrollWidget.session().getRenderScrollPositionY();
    }
    
    public LssProperty<Float> scrollButtonClickOffset() {
        return this.scrollButtonClickOffset;
    }
    
    public LssProperty<Boolean> useLssPosition() {
        return this.useLssPosition;
    }
    
    public LssProperty<Integer> scrollColor() {
        return this.scrollColor;
    }
    
    public LssProperty<Integer> scrollHoverColor() {
        return this.scrollHoverColor;
    }
    
    public LssProperty<Integer> scrollBackgroundColor() {
        return this.scrollBackgroundColor;
    }
    
    public LssProperty<Integer> minScrollHeight() {
        return this.minScrollHeight;
    }
}
