// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ScrollWidget extends SimpleWidget
{
    private static final ModifyReason CONTENT_BOUNDS;
    private static final ModifyReason SCROLLBAR_AUTO_BOUNDS;
    private final ListSession<?> session;
    private final Widget contentWidget;
    private final ScrollbarWidget scrollbarWidget;
    private final LssProperty<VerticalAlignment> childAlign;
    private final LssProperty<Boolean> enableScrollContent;
    private final LssProperty<Float> scrollSpeed;
    private final LssProperty<Boolean> scrollAlwaysVisible;
    private final LssProperty<Boolean> moveDirtBackground;
    private final LssProperty<VerticalAlignment> fixedPosition;
    private final LssProperty<Boolean> autoScroll;
    private final LssProperty<Boolean> modifyContentWidth;
    private float pressedScrollPositionY;
    private boolean dragging;
    private boolean rendered;
    
    public ScrollWidget(final SessionedListWidget<?> listWidget) {
        this(listWidget, listWidget.listSession());
    }
    
    public ScrollWidget(final VerticalListWidget<?> listWidget) {
        this(listWidget, listWidget.listSession());
    }
    
    public ScrollWidget(final Widget widget, final ListSession<?> session) {
        this.childAlign = new LssProperty<VerticalAlignment>(VerticalAlignment.TOP);
        this.enableScrollContent = new LssProperty<Boolean>(false);
        this.scrollSpeed = new LssProperty<Float>(-1.0f);
        this.scrollAlwaysVisible = new LssProperty<Boolean>(false);
        this.moveDirtBackground = new LssProperty<Boolean>(true);
        this.fixedPosition = new LssProperty<VerticalAlignment>(null);
        this.autoScroll = new LssProperty<Boolean>(false);
        this.modifyContentWidth = new LssProperty<Boolean>(true);
        this.contentWidget = widget;
        this.session = session;
        this.scrollbarWidget = new ScrollbarWidget(this);
        this.updateBounds();
    }
    
    private void scrollBackground(final float delta) {
        final String scrollBackgroundRenderer = this.labyAPI.themeService().currentTheme().metadata().get("scroll_background");
        if (this.moveDirtBackground.get() && scrollBackgroundRenderer != null) {
            Parent parent = this.getParent();
            if (Objects.isNull(parent)) {
                return;
            }
            do {
                if (parent instanceof AbstractWidget) {
                    final AbstractWidget<?> w = (AbstractWidget<?>)parent;
                    if (!w.renderer().get().getName().equals(scrollBackgroundRenderer)) {
                        continue;
                    }
                    final LssProperty<Integer> backgroundDirtShift = w.backgroundDirtShift();
                    backgroundDirtShift.set((int)this.session.getScrollPositionY());
                    break;
                }
            } while ((parent = parent.getParent()) != null);
        }
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.addChild(this.contentWidget);
        ((AbstractWidget<ScrollbarWidget>)this).addChild(this.scrollbarWidget);
        this.rendered = false;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        this.updateScrollbarBounds();
        this.updateScrollContentOffset();
        this.updateContentBounds();
        super.renderWidget(context);
        this.rendered = true;
    }
    
    private void updateScrollContentOffset() {
        float scrollOffset = this.session.getRenderScrollPositionY();
        if (!this.isUsingFloatingPointPosition()) {
            scrollOffset = (float)Math.ceil(scrollOffset);
        }
        this.contentWidget.setTranslateY(-scrollOffset);
    }
    
    @Override
    protected void updateContentBounds() {
        final float scrollBarWidth = (this.scrollAlwaysVisible.get() || this.isScrollbarRequired() || !this.modifyContentWidth.get()) ? this.scrollbarWidget.bounds().getWidth(BoundsType.OUTER) : 0.0f;
        final Bounds contentBounds = this.contentWidget.bounds();
        final Bounds scrollbarBounds = this.scrollbarWidget.bounds();
        final Bounds bounds = this.bounds();
        contentBounds.setOuterPosition(bounds.getLeft(BoundsType.INNER), bounds.getTop(BoundsType.INNER), ScrollWidget.CONTENT_BOUNDS);
        if (!this.hasSize(WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
            contentBounds.setOuterWidth(bounds.getWidth(BoundsType.INNER) - scrollBarWidth, ScrollWidget.CONTENT_BOUNDS);
        }
        if (!this.scrollbarWidget.useLssPosition().get()) {
            scrollbarBounds.setOuterX(bounds.getRight(BoundsType.INNER) - scrollBarWidth, ScrollWidget.SCROLLBAR_AUTO_BOUNDS);
            scrollbarBounds.setOuterY(bounds.getTop(BoundsType.INNER), ScrollWidget.SCROLLBAR_AUTO_BOUNDS);
            scrollbarBounds.setOuterRight(bounds.getRight(BoundsType.INNER), ScrollWidget.SCROLLBAR_AUTO_BOUNDS);
            scrollbarBounds.setOuterBottom(bounds.getBottom(BoundsType.INNER), ScrollWidget.SCROLLBAR_AUTO_BOUNDS);
        }
        super.updateContentBounds();
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (this.rendered && !this.didBoundsChangeThisFrame()) {
            this.updateScrollbarBounds();
        }
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        float left;
        float right;
        if (this.scrollbarWidget.useLssPosition().get()) {
            left = Math.min(this.contentWidget.bounds().getLeft(BoundsType.OUTER), this.isScrollbarRequired() ? this.scrollbarWidget.bounds().getLeft(BoundsType.OUTER) : 2.1474836E9f);
            right = Math.max(this.contentWidget.bounds().getRight(BoundsType.OUTER), this.isScrollbarRequired() ? this.scrollbarWidget.bounds().getRight(BoundsType.OUTER) : -2.1474836E9f);
        }
        else {
            left = this.contentWidget.bounds().getLeft(BoundsType.OUTER);
            right = this.contentWidget.bounds().getRight(BoundsType.OUTER) + this.scrollbarWidget.bounds().getWidth(BoundsType.OUTER);
        }
        return right - left + this.bounds().getHorizontalOffset(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return this.contentWidget.bounds().getHeight(BoundsType.OUTER) + this.bounds().getVerticalOffset(type);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        this.pressedScrollPositionY = this.session.getScrollPositionY() + mouse.getY();
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (!this.enableScrollContent.get() || !this.contentWidget.bounds().isInRectangle(mouse)) {
            return super.mouseDragged(mouse, button, deltaX, deltaY);
        }
        if (super.mouseDragged(mouse, button, deltaX, deltaY)) {
            return true;
        }
        final Bounds bounds = this.bounds();
        if (this.isScrollbarRequired() && (bounds.isInRectangle((float)mouse.getX(), (float)mouse.getY()) || this.dragging)) {
            final float previousPosition = this.session.getScrollPositionY();
            this.session.setScrollPositionY(this.pressedScrollPositionY - mouse.getY());
            this.updateScrollbarBounds();
            this.scrolled(previousPosition, this.session.getScrollPositionY());
            return this.dragging = true;
        }
        return false;
    }
    
    private float getScrollSpeed() {
        return Objects.equals(this.scrollSpeed.get(), this.scrollSpeed.defaultValue()) ? (this.contentWidget.getContentHeight(BoundsType.OUTER) / this.contentWidget.getChildren().size()) : this.scrollSpeed.get();
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        if (this.isScrollbarRequired()) {
            final float previousPosition = this.session.getScrollPositionY();
            final float scrollSpeed = this.getScrollSpeed();
            final float delta = (float)(-scrollDelta) * scrollSpeed * ((this.childAlign.get() == VerticalAlignment.BOTTOM) ? -1 : 1);
            this.session.scroll(delta);
            this.updateScrollbarBounds();
            this.scrolled(previousPosition, this.session.getScrollPositionY());
            if (this.isHovered()) {
                return true;
            }
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        this.dragging = false;
        return super.mouseReleased(mouse, mouseButton);
    }
    
    public void updateScrollbarBounds() {
        final float prevMaxY = this.session.maxScrollPositionY();
        final boolean prevBottom = this.session.isScrolledToBottom();
        final boolean prevRequired = this.session.isScrollbarRequired();
        final float contentHeight = this.contentWidget.getContentHeight(BoundsType.OUTER);
        final float overflow = Math.max(contentHeight - this.bounds().getHeight(BoundsType.INNER), 0.0f);
        this.session.updateBounds(contentHeight, overflow, this.fixedPosition.get());
        if (this.autoScroll.get() && (prevBottom || !prevRequired) && this.session.maxScrollPositionY() != prevMaxY) {
            this.session.scrollToBottom();
            this.session.skipAnimation();
        }
    }
    
    public void scrolled(final float from, final float to) {
        final float delta = to - from;
        if (delta != 0.0f) {
            this.updateScrollContentOffset();
            this.scrollBackground(delta);
        }
    }
    
    public float getOverflowHeight() {
        final float overflow = this.session.getContentHeight() - this.bounds().getHeight(BoundsType.INNER);
        return Math.max(overflow, 0.0f);
    }
    
    public boolean isScrollbarRequired() {
        return this.scrollbarWidget.isVisible() && this.getOverflowHeight() > 0.0f;
    }
    
    public float getTopLeftSpace() {
        return this.getOverflowHeight() - this.session.getScrollPositionY();
    }
    
    public void scrollToBottom() {
        this.scrollTo(this.getOverflowHeight());
    }
    
    public void scrollToTop() {
        this.scrollTo(0.0f);
    }
    
    public void scrollToSelectedChild() {
        final Widget selected = (Widget)this.session.getSelectedEntry();
        if (selected == null) {
            return;
        }
        this.scrollTo(selected.bounds().getTop() - this.contentWidget.bounds().getTop());
    }
    
    private void scrollTo(final float targetPosition) {
        final float previousPosition = this.session.getScrollPositionY();
        this.session.setScrollPositionY(targetPosition);
        this.scrolled(previousPosition, this.session.getScrollPositionY());
    }
    
    @Override
    protected boolean isVisibleForContentBounds(final Widget child) {
        return (this.scrollbarWidget != child || this.scrollbarWidget.useLssPosition().get()) && super.isVisibleForContentBounds(child);
    }
    
    public float getBottomLeftSpace() {
        return this.session.getScrollPositionY();
    }
    
    public ListSession<?> session() {
        return this.session;
    }
    
    public Widget contentWidget() {
        return this.contentWidget;
    }
    
    public LssProperty<VerticalAlignment> childAlign() {
        return this.childAlign;
    }
    
    public LssProperty<Boolean> enableScrollContent() {
        return this.enableScrollContent;
    }
    
    public LssProperty<Float> scrollSpeed() {
        return this.scrollSpeed;
    }
    
    public LssProperty<Boolean> scrollAlwaysVisible() {
        return this.scrollAlwaysVisible;
    }
    
    public LssProperty<Boolean> moveDirtBackground() {
        return this.moveDirtBackground;
    }
    
    public LssProperty<VerticalAlignment> fixedPosition() {
        return this.fixedPosition;
    }
    
    public LssProperty<Boolean> autoScroll() {
        return this.autoScroll;
    }
    
    public LssProperty<Boolean> modifyContentWidth() {
        return this.modifyContentWidth;
    }
    
    public ScrollbarWidget scrollbar() {
        return this.scrollbarWidget;
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        if ((type == AutoAlignType.HEIGHT && this.hasSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)) || (type == AutoAlignType.WIDTH && this.hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT))) {
            return false;
        }
        if (type == AutoAlignType.WIDTH) {
            return child != this.scrollbarWidget;
        }
        return type == AutoAlignType.POSITION && (child == this.contentWidget || (child == this.scrollbarWidget && !this.scrollbarWidget.useLssPosition().get()));
    }
    
    static {
        CONTENT_BOUNDS = ModifyReason.of("contentBounds");
        SCROLLBAR_AUTO_BOUNDS = ModifyReason.of("scrollbarAutoBounds");
    }
}
