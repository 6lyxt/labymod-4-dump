// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.util;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Iterator;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ChatHistoryWidget extends AbstractWidget<Widget>
{
    private static final ModifyReason LIST_CONTENT;
    private final LssProperty<Float> spaceBetweenEntries;
    protected final ListSession<Widget> session;
    protected final List<Widget> history;
    
    public ChatHistoryWidget(final ListSession<Widget> listSession) {
        this.spaceBetweenEntries = new LssProperty<Float>(1.0f);
        this.history = new ArrayList<Widget>();
        this.session = listSession;
    }
    
    @Override
    public void render(final ScreenContext context) {
        this.fillSpaceFromHistory();
        super.render(context);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final ReasonableMutableRectangle rectangle = this.parent.bounds().rectangle(BoundsType.MIDDLE);
        for (final Widget child : this.children) {
            final Bounds bounds = child.bounds();
            child.setOutOfBounds(!rectangle.isOverlapping(bounds.getX() + this.getTranslateX(), bounds.getY() + this.getTranslateY(), bounds.getWidth(), bounds.getHeight()));
        }
        for (final Widget widget : this.children) {
            if (widget.isOutOfBounds()) {
                continue;
            }
            widget.render(context);
        }
    }
    
    @Override
    protected void updateContentBounds() {
        final float spaceBetweenEntries = this.spaceBetweenEntries.get();
        float y = this.bounds().getBottom();
        for (final Widget child : this.children) {
            final Bounds bounds = child.bounds();
            y -= bounds.getHeight();
            bounds.setY(y, ChatHistoryWidget.LIST_CONTENT);
            y -= spaceBetweenEntries;
        }
        super.updateContentBounds();
    }
    
    public void addToHistory(final Widget widget) {
        this.history.add(widget);
    }
    
    public void clearHistory() {
        this.history.clear();
    }
    
    private void fillSpaceFromHistory() {
        boolean added = false;
        final boolean empty = this.children.isEmpty();
        while (this.hasSpace() && !this.history.isEmpty()) {
            this.addChildAndKeepScrollPosition(this.history.remove(0), false);
            added = true;
            if (!empty) {
                break;
            }
        }
        if (added) {
            this.sortChildren();
        }
    }
    
    private void addChildAndKeepScrollPosition(final Widget widget, final boolean shouldSort) {
        final float contentHeight = this.getContentHeight(BoundsType.OUTER);
        final float scrollPosYBottom = contentHeight - this.session.getScrollPositionY();
        final float prevScrollPosYBottom = contentHeight - this.session.getPreviousScrollPositionY();
        this.addChildInitialized(widget, shouldSort);
        final float newContentHeight = this.getContentHeight(BoundsType.OUTER);
        this.session.setScrollPositionY(newContentHeight - scrollPosYBottom);
        this.session.setPreviousScrollPositionY(newContentHeight - prevScrollPosYBottom);
        this.session.skipAnimation();
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        float height = 0.0f;
        final float spaceBetweenEntries = this.spaceBetweenEntries.get();
        for (final Widget child : this.children) {
            if (!child.isVisible() && !child.isDragging()) {
                continue;
            }
            height += child.bounds().getHeight(BoundsType.OUTER) + spaceBetweenEntries;
        }
        if (height != 0.0f) {
            height -= spaceBetweenEntries;
        }
        return height + this.bounds().getVerticalOffset(type);
    }
    
    private boolean hasSpace() {
        final float contentHeight = this.getContentHeight(BoundsType.OUTER);
        final float scrollOffsetTop = this.session.getScrollPositionY();
        final float viewportHeight = this.parent.bounds().getHeight();
        final float scrollOffsetBottom = Math.max(contentHeight - scrollOffsetTop - viewportHeight, 0.0f);
        final float contentHeightOutOfView = Math.max(contentHeight - scrollOffsetBottom - viewportHeight, 0.0f);
        final float thresholdHeight = viewportHeight / 2.0f;
        return contentHeightOutOfView < thresholdHeight;
    }
    
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    static {
        LIST_CONTENT = ModifyReason.of("listContent");
    }
}
