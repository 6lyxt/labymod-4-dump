// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import net.labymod.api.property.Property;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.util.ListOrder;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class VerticalListWidget<T extends Widget> extends SessionedListWidget<T>
{
    private static final ModifyReason LIST_CONTENT;
    private final LssProperty<Boolean> overwriteWidth;
    private final LssProperty<Boolean> squeezeHeight;
    private final LssProperty<Boolean> selectable;
    private final LssProperty<Float> spaceBetweenEntries;
    private final LssProperty<Boolean> renderOutOfBounds;
    private final LssProperty<VerticalAlignment> listAlignment;
    private final LssProperty<ListOrder> listOrder;
    private Selectable<T> selectCallback;
    private Selectable<T> doubleClickCallback;
    private long timeLastEntrySelected;
    
    public VerticalListWidget() {
        this((ListSession)new ListSession());
    }
    
    public VerticalListWidget(final ListSession<T> session) {
        super(session);
        this.overwriteWidth = new LssProperty<Boolean>(true);
        this.squeezeHeight = new LssProperty<Boolean>(false);
        this.selectable = new LssProperty<Boolean>(false);
        this.spaceBetweenEntries = new LssProperty<Float>(1.0f);
        this.renderOutOfBounds = new LssProperty<Boolean>(false);
        this.listAlignment = new LssProperty<VerticalAlignment>(VerticalAlignment.TOP);
        this.listOrder = new LssProperty<ListOrder>(ListOrder.NORMAL);
        this.timeLastEntrySelected = 0L;
        this.translateY().addChangeListener((property, oldValue, newValue) -> this.updateVisibility(this, this.parent));
    }
    
    @Override
    public void postInitialize() {
        super.postInitialize();
        for (final T child : this.children) {
            if (this.session.compareSelectedTo(child)) {
                this.session.setSelectedEntry(child);
            }
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        for (final T child : this.children) {
            child.setSelected(this.selectable.get() && child.equals(this.session.getSelectedEntry()));
        }
        this.updateOutOfBoundsState();
        for (final T widget : this.children) {
            if (!this.renderOutOfBounds.get() && widget.isOutOfBounds()) {
                continue;
            }
            widget.render(context);
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        T interacted = null;
        for (final T entry : this.children) {
            if (entry.isHovered() && entry.isInteractable()) {
                interacted = entry;
                break;
            }
        }
        if (interacted != null && mouseButton == MouseButton.LEFT) {
            interacted.setDragging(true);
            for (final T child : this.children) {
                child.setSelected(this.selectable.get() && child == interacted);
            }
            boolean handled = interacted.mouseClicked(mouse, mouseButton);
            if (this.selectable.get()) {
                final T selectedEntry = this.session.getSelectedEntry();
                this.session.setSelectedEntry(interacted);
                if (!handled) {
                    handled = this.handleCallback(selectedEntry, interacted);
                }
            }
            return handled;
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (!this.selectable().get() || (key != Key.ARROW_UP && key != Key.ARROW_DOWN)) {
            return super.keyPressed(key, type);
        }
        final T selected = this.session.getSelectedEntry();
        if (selected == null) {
            return super.keyPressed(key, type);
        }
        final int index = this.children.indexOf(selected);
        if (index == -1) {
            return super.keyPressed(key, type);
        }
        final int multiplier = (key == Key.ARROW_UP) ? -1 : 1;
        final int targetIndex = index + multiplier;
        if (targetIndex < 0 || targetIndex >= this.children.size()) {
            return super.keyPressed(key, type);
        }
        T target = null;
        for (int i = 0; i < this.children.size(); ++i) {
            final T child = this.children.get(i);
            child.setSelected(i == targetIndex);
            if (i == targetIndex) {
                target = child;
            }
        }
        if (target != null) {
            this.session.setSelectedEntry(target);
            if (this.selectCallback != null) {
                this.selectCallback.select(target);
            }
            final float scrollDelta = target.bounds().getHeight(BoundsType.OUTER) * multiplier;
            if (this.parent instanceof ScrollWidget) {
                final Rectangle rect = target.bounds().rectangle(BoundsType.MIDDLE);
                if (!this.parent.bounds().isOverlapping(rect.getX() + this.getTranslateX(), rect.getY() + this.getTranslateY() + scrollDelta, rect.getWidth(), rect.getHeight())) {
                    this.session.scroll(scrollDelta + this.spaceBetweenEntries.get());
                }
            }
            else {
                this.session.scroll(scrollDelta + this.spaceBetweenEntries.get());
            }
        }
        if (this.session.getEntrySwapper() != null && KeyHandler.isShiftDown()) {
            this.session.getEntrySwapper().swap(index, targetIndex);
            this.session.setSelectedEntry(this.children.get(targetIndex));
        }
        return true;
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        float width = 0.0f;
        for (final T child : this.children) {
            if (!child.isVisible() && !child.isDragging()) {
                continue;
            }
            width = Math.max(width, child.bounds().getWidth(BoundsType.OUTER));
        }
        return width + this.bounds().getHorizontalOffset(type);
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
    
    @Override
    protected void updateContentBounds() {
        final boolean reverse = this.listOrder.get() == ListOrder.REVERSE;
        float fullHeight = 0.0f;
        final int size = this.children.size();
        int i = reverse ? (size - 1) : 0;
        float contentWidth = this.bounds().getWidth(BoundsType.INNER);
        final boolean overwriteWidth = this.overwriteWidth.get();
        if (overwriteWidth && this.hasSize(WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
            contentWidth = 0.0f;
            for (final T child : this.children) {
                if (!child.isVisible()) {
                    continue;
                }
                contentWidth = Math.max(contentWidth, child.getContentWidth(BoundsType.OUTER));
            }
            final float minWidth = this.getSizeOr(SizeType.MIN, WidgetSide.WIDTH, 0.0f);
            final float maxWidth = this.getSizeOr(SizeType.MAX, WidgetSide.WIDTH, 0.0f);
            if (contentWidth < minWidth) {
                contentWidth = minWidth;
            }
            else if (maxWidth > 0.0f && contentWidth > maxWidth) {
                contentWidth = maxWidth;
            }
        }
        final VerticalAlignment listAlignment = this.listAlignment.get();
        final float spaceBetweenEntries = this.spaceBetweenEntries.get();
        while (i >= 0 && i < size) {
            final Widget child2 = this.children.get(i);
            final boolean lastChild = i == (reverse ? 0 : (size - 1));
            if (reverse) {
                --i;
            }
            else {
                ++i;
            }
            if (!child2.isVisible()) {
                continue;
            }
            final Bounds childBounds = child2.bounds();
            final Bounds bounds = this.bounds();
            float xOffset = 0.0f;
            switch (child2.alignmentX().get()) {
                case LEFT: {
                    xOffset = 0.0f;
                    break;
                }
                case CENTER: {
                    xOffset = bounds.getWidth(BoundsType.INNER) / 2.0f - childBounds.getWidth(BoundsType.OUTER) / 2.0f;
                    break;
                }
                case RIGHT: {
                    xOffset = bounds.getWidth(BoundsType.INNER) - childBounds.getWidth(BoundsType.OUTER);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(child2.alignmentX().get()));
                }
            }
            switch (listAlignment) {
                case BOTTOM: {
                    final float height = childBounds.getHeight(BoundsType.OUTER);
                    childBounds.setOuterPosition(bounds.getLeft(BoundsType.INNER) + xOffset, bounds.getBottom(BoundsType.INNER) - fullHeight - height, VerticalListWidget.LIST_CONTENT);
                    break;
                }
                case TOP: {
                    childBounds.setOuterPosition(bounds.getLeft(BoundsType.INNER) + xOffset, bounds.getTop(BoundsType.INNER) + fullHeight, VerticalListWidget.LIST_CONTENT);
                    break;
                }
            }
            if (overwriteWidth) {
                childBounds.setOuterWidth(contentWidth, VerticalListWidget.LIST_CONTENT);
            }
            if (lastChild && this.squeezeHeight.get()) {
                childBounds.setOuterBottom(bounds.getBottom(BoundsType.INNER), VerticalListWidget.LIST_CONTENT);
            }
            fullHeight += childBounds.getHeight(BoundsType.OUTER) + spaceBetweenEntries;
            child2.updateBounds();
        }
        super.updateContentBounds();
        this.updateOutOfBoundsState();
    }
    
    private void updateOutOfBoundsState() {
        if (this.parent == null) {
            return;
        }
        final ReasonableMutableRectangle rectangle = this.parent.bounds().rectangle(BoundsType.MIDDLE);
        for (final T child : this.children) {
            final Rectangle rect = child.bounds().rectangle(BoundsType.MIDDLE);
            final boolean outOfBounds = !rectangle.isOverlapping(rect.getX() + this.getTranslateX(), rect.getY() + this.getTranslateY(), rect.getWidth(), rect.getHeight());
            child.setOutOfBounds(outOfBounds || !child.isVisible());
        }
    }
    
    private boolean handleCallback(final T previousEntry, final T entry) {
        boolean handled = false;
        if (this.selectCallback != null && previousEntry != entry) {
            this.updateTimeLastEntrySelected();
            this.selectCallback.select(entry);
            handled = true;
        }
        if (this.doubleClickCallback != null && previousEntry == entry && this.timeLastEntrySelected + 400L > TimeUtil.getMillis()) {
            this.doubleClickCallback.select(entry);
            handled = true;
            this.timeLastEntrySelected = 0L;
        }
        this.updateTimeLastEntrySelected();
        return handled;
    }
    
    public void setSelectCallback(final Selectable<T> callback) {
        this.selectCallback = callback;
    }
    
    public void setDoubleClickCallback(final Selectable<T> doubleClickCallback) {
        this.doubleClickCallback = doubleClickCallback;
    }
    
    @Deprecated
    public ListSession<T> session() {
        return this.session;
    }
    
    public LssProperty<Boolean> overwriteWidth() {
        return this.overwriteWidth;
    }
    
    public LssProperty<Boolean> squeezeHeight() {
        return this.squeezeHeight;
    }
    
    public LssProperty<Boolean> selectable() {
        return this.selectable;
    }
    
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    public LssProperty<Boolean> renderOutOfBounds() {
        return this.renderOutOfBounds;
    }
    
    public LssProperty<VerticalAlignment> listAlignment() {
        return this.listAlignment;
    }
    
    public LssProperty<ListOrder> listOrder() {
        return this.listOrder;
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        if (type == AutoAlignType.HEIGHT && this.hasSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)) {
            return false;
        }
        if (type == AutoAlignType.WIDTH) {
            return this.overwriteWidth.get();
        }
        if (type != AutoAlignType.HEIGHT) {
            return type != AutoAlignType.WIDTH || this.overwriteWidth.get();
        }
        if (!this.squeezeHeight.get()) {
            return false;
        }
        final boolean reverse = this.listOrder.get() == ListOrder.REVERSE;
        final int i = this.children.indexOf(child);
        return i == (reverse ? 0 : (this.children.size() - 1));
    }
    
    private void updateTimeLastEntrySelected() {
        this.timeLastEntrySelected = TimeUtil.getMillis();
    }
    
    static {
        LIST_CONTENT = ModifyReason.of("listContent");
    }
}
