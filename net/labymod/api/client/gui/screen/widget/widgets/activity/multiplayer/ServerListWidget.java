// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer;

import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.network.server.storage.MoveActionType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class ServerListWidget<Entry extends ServerEntryWidget> extends VerticalListWidget<Entry>
{
    private static final ModifyReason LIST_CONTENT;
    private ServerMover<Entry> mover;
    private MoveActionType actionType;
    private MoveActionType prevActionType;
    private MoveActionType dropActionType;
    private Entry draggingWidget;
    private Entry targetWidget;
    private Entry prevTargetWidget;
    private Entry dropTargetWidget;
    private Entry releasedDraggingWidget;
    private float draggingOffsetX;
    private float draggingOffsetY;
    private float releasedDraggingFromX;
    private float releasedDraggingFromY;
    private float releasedDraggingToX;
    private float releasedDraggingToY;
    private float mouseClickX;
    private float mouseClickY;
    private long targetTimePassed;
    private long draggingTimeReleased;
    private boolean canStartDragging;
    private boolean mouseMoved;
    private boolean keepOldPosition;
    private boolean dropListVisible;
    
    public ServerListWidget(final ListSession<Entry> session) {
        super(session);
        this.prevActionType = MoveActionType.NONE;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
    }
    
    @Override
    public void postInitialize() {
        super.postInitialize();
        if (this.releasedDraggingWidget != null) {
            final String identifier = this.releasedDraggingWidget.toString();
            Entry target = null;
            boolean hasMultiple = false;
            for (final Entry child : this.children) {
                if (child.toString().equals(identifier)) {
                    if (target != null) {
                        hasMultiple = true;
                        break;
                    }
                    target = child;
                }
            }
            this.releasedDraggingWidget = (Entry)(hasMultiple ? null : target);
            this.draggingTimeReleased = TimeUtil.getCurrentTimeMillis();
            this.updateContentBounds();
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        for (final Entry child : this.children) {
            child.setSelected(this.selectable().get() && child.equals(this.session.getSelectedEntry()));
        }
        if (this.releasedDraggingWidget != null) {
            final Bounds bounds = this.releasedDraggingWidget.bounds();
            float progress = Math.min(1.0f, (TimeUtil.getCurrentTimeMillis() - this.draggingTimeReleased) / 100.0f);
            progress = (float)CubicBezier.EASE_OUT.curve(progress);
            if (progress > 0.0f && progress < 1.0f) {
                bounds.setOuterPosition(this.releasedDraggingFromX + (this.releasedDraggingToX - this.releasedDraggingFromX) * progress, this.releasedDraggingFromY + (this.releasedDraggingToY - this.releasedDraggingFromY) * progress, ServerListWidget.LIST_CONTENT);
            }
            else {
                this.releasedDraggingWidget = null;
                this.updateContentBounds();
            }
        }
        for (final Entry widget : this.children) {
            if (!this.renderOutOfBounds().get() && widget.isOutOfBounds()) {
                continue;
            }
            if (widget == this.draggingWidget) {
                continue;
            }
            widget.render(context);
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.labyAPI.config().multiplayer().serverList().draggableEntries().get()) {
            this.canStartDragging = true;
            this.mouseClickX = (float)mouse.getX();
            this.mouseClickY = (float)mouse.getY();
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        final float offsetX = Math.abs(mouse.getX() - this.mouseClickX);
        final float offsetY = Math.abs(mouse.getY() - this.mouseClickY);
        final boolean hasMoved = offsetX > 10.0f || offsetY > 10.0f;
        if (hasMoved) {
            this.mouseMoved = true;
        }
        if (this.draggingWidget != null && this.draggingOffsetX == 0.0f && this.draggingOffsetY == 0.0f) {
            final Bounds bounds = this.draggingWidget.bounds();
            this.draggingOffsetX = this.mouseClickX - bounds.getX();
            this.draggingOffsetY = this.mouseClickY - bounds.getY();
        }
        if (this.mover != null && this.draggingWidget == null && button == MouseButton.LEFT && this.canStartDragging && hasMoved) {
            this.canStartDragging = false;
            final List<Entry> targetWidgets = this.getChildrenAt((int)this.mouseClickX, (int)this.mouseClickY);
            if (!targetWidgets.isEmpty()) {
                this.draggingWidget = targetWidgets.get(0);
                this.keepOldPosition = false;
                final int size = this.children.size();
                if (size != 1) {
                    final int index = this.children.indexOf(this.draggingWidget);
                    final boolean below = size > index + 1;
                    final Entry target = below ? this.children.get(index + 1) : this.children.get(index - 1);
                    this.setTargetAction(target, below ? MoveActionType.INSERT_ABOVE : MoveActionType.INSERT_BELOW);
                }
                this.keepOldPosition = true;
                this.prevActionType = MoveActionType.NONE;
                this.updateContentBounds();
                return true;
            }
        }
        if (this.draggingWidget != null) {
            this.draggingWidget.bounds().setOuterPosition(mouse.getX() - this.draggingOffsetX, mouse.getY() - this.draggingOffsetY, ServerListWidget.LIST_CONTENT);
            this.updateContentBounds();
            if (this.dropListVisible) {
                this.addIdSilent("drop-list");
            }
            else {
                this.removeIdSilent("drop-list");
            }
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.draggingWidget != null && this.mover != null) {
            final Rectangle bounds = this.draggingWidget.bounds();
            this.releasedDraggingWidget = this.draggingWidget;
            this.draggingTimeReleased = TimeUtil.getCurrentTimeMillis();
            this.releasedDraggingFromX = bounds.getX();
            this.releasedDraggingFromY = bounds.getY();
            this.mover.move(this.draggingWidget, this.dropTargetWidget, this.dropActionType);
            this.draggingWidget = null;
            this.actionType = MoveActionType.NONE;
            this.targetWidget = null;
            this.dropTargetWidget = null;
            this.dropActionType = MoveActionType.NONE;
            this.removeId("drop-list");
            this.updateContentBounds();
        }
        this.canStartDragging = false;
        this.draggingOffsetX = 0.0f;
        this.draggingOffsetY = 0.0f;
        return super.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    protected void updateContentBounds() {
        final float spaceBetweenEntries = this.spaceBetweenEntries().get();
        float draggingWidgetHeight = 0.0f;
        if (this.draggingWidget != null) {
            draggingWidgetHeight = this.draggingWidget.bounds().getHeight(BoundsType.OUTER) + spaceBetweenEntries;
            final Rectangle draggingBounds = this.draggingWidget.bounds().rectangle(BoundsType.INNER);
            final float draggingY = draggingBounds.getY() + this.draggingOffsetY;
            boolean hasTarget = false;
            Entry firstVisibleChild = null;
            Entry lastVisibleChild = null;
            float offsetY = 0.0f;
            for (final Entry child : this.children) {
                if (!child.isVisible()) {
                    continue;
                }
                if (child == this.draggingWidget) {
                    if (!this.keepOldPosition) {
                        continue;
                    }
                    offsetY -= draggingWidgetHeight;
                }
                else {
                    if (child == this.targetWidget && this.actionType == MoveActionType.INSERT_ABOVE) {
                        offsetY += draggingWidgetHeight;
                    }
                    final Rectangle childBounds = child.bounds().rectangle(BoundsType.OUTER);
                    final float childHeight = childBounds.getHeight();
                    final float childY = childBounds.getY() - offsetY;
                    final float slice = childHeight / 3.0f;
                    final boolean isInBetween = draggingY >= childY + slice && draggingY <= childY + childHeight - slice;
                    final boolean isAbove = draggingY >= childY && draggingY < childY + slice;
                    final boolean isBelow = draggingY > childY + childHeight - slice && draggingY <= childY + childHeight;
                    if (isAbove) {
                        this.setTargetAction(child, MoveActionType.INSERT_ABOVE);
                        hasTarget = true;
                        break;
                    }
                    if (isBelow) {
                        this.setTargetAction(child, MoveActionType.INSERT_BELOW);
                        hasTarget = true;
                        break;
                    }
                    if (isInBetween) {
                        this.setTargetAction(child, MoveActionType.ADD_TO_FOLDER);
                        hasTarget = true;
                        break;
                    }
                    if (child == this.targetWidget && this.actionType == MoveActionType.INSERT_BELOW) {
                        offsetY += draggingWidgetHeight;
                    }
                    if (draggingY > childY + childHeight - slice) {
                        lastVisibleChild = child;
                    }
                    if (firstVisibleChild != null || draggingY >= childY) {
                        continue;
                    }
                    firstVisibleChild = child;
                }
            }
            if (draggingY < this.bounds().getY()) {
                this.setTargetAction(firstVisibleChild, MoveActionType.NONE);
            }
            if (!hasTarget && lastVisibleChild != null) {
                this.setTargetAction(lastVisibleChild, this.keepOldPosition ? MoveActionType.NONE : MoveActionType.INSERT_BELOW);
            }
        }
        final long timePassedSinceTargetUpdate = TimeUtil.getCurrentTimeMillis() - this.targetTimePassed;
        float transitionProgress = this.keepOldPosition ? 1.0f : Math.min(1.0f, timePassedSinceTargetUpdate / 200.0f);
        transitionProgress = (float)CubicBezier.EASE_IN_OUT.curve(transitionProgress);
        float y = 0.0f;
        final ReasonableMutableRectangle bounds = this.bounds().rectangle(BoundsType.INNER);
        for (final Entry child2 : this.children) {
            if (child2.isVisible()) {
                if (child2 == this.draggingWidget) {
                    continue;
                }
                if (child2 == this.targetWidget && this.actionType == MoveActionType.INSERT_ABOVE) {
                    y += draggingWidgetHeight * transitionProgress;
                }
                if (child2 == this.prevTargetWidget && this.prevActionType == MoveActionType.INSERT_ABOVE) {
                    y += draggingWidgetHeight * (1.0f - transitionProgress);
                }
                final Bounds childBounds2 = child2.bounds();
                if (this.releasedDraggingWidget != null && this.releasedDraggingWidget.getListIndex() == child2.getListIndex()) {
                    this.releasedDraggingToX = bounds.getX();
                    this.releasedDraggingToY = bounds.getY() + y;
                }
                else {
                    childBounds2.setOuterPosition(bounds.getLeft(), bounds.getTop() + y, ServerListWidget.LIST_CONTENT);
                }
                childBounds2.setOuterWidth(bounds.getWidth(), ServerListWidget.LIST_CONTENT);
                if (child2 == this.targetWidget && this.actionType == MoveActionType.INSERT_BELOW) {
                    y += draggingWidgetHeight * transitionProgress;
                }
                if (child2 == this.prevTargetWidget && this.prevActionType == MoveActionType.INSERT_BELOW) {
                    y += draggingWidgetHeight * (1.0f - transitionProgress);
                }
                y += childBounds2.getHeight(BoundsType.OUTER) + spaceBetweenEntries;
            }
        }
        this.handleSizeAttributes();
    }
    
    private void setTargetAction(final Entry target, final MoveActionType actionType) {
        if (target != this.targetWidget || actionType != this.actionType) {
            final long timePassedSinceTargetUpdate = TimeUtil.getCurrentTimeMillis() - this.targetTimePassed;
            if (timePassedSinceTargetUpdate < 200L || !this.mouseMoved) {
                return;
            }
            if (this.keepOldPosition) {
                Entry prev = null;
                Entry next = null;
                boolean found = false;
                for (final Entry child : this.children) {
                    if (child == this.draggingWidget) {
                        found = true;
                    }
                    else {
                        if (found) {
                            next = child;
                            break;
                        }
                        prev = child;
                    }
                }
                if (target == prev && actionType == MoveActionType.INSERT_BELOW) {
                    return;
                }
                if (target == next && actionType == MoveActionType.INSERT_ABOVE) {
                    return;
                }
                if (actionType == MoveActionType.ADD_TO_FOLDER || actionType == MoveActionType.NONE) {
                    if (this.prevTargetWidget != target || this.prevActionType != actionType) {
                        this.dropTargetWidget = target;
                        this.dropListVisible = ((this.dropActionType = actionType) == MoveActionType.ADD_TO_FOLDER);
                    }
                    return;
                }
                this.keepOldPosition = false;
            }
            this.prevTargetWidget = this.targetWidget;
            this.prevActionType = this.actionType;
            this.targetWidget = target;
            this.actionType = actionType;
            this.targetTimePassed = TimeUtil.getCurrentTimeMillis();
            this.dropTargetWidget = target;
            this.dropActionType = actionType;
            this.dropListVisible = (actionType == MoveActionType.ADD_TO_FOLDER);
            this.mouseMoved = false;
        }
    }
    
    private void addIdSilent(final String id) {
        if (!this.hasId(id)) {
            this.getIds().add(id);
            this.reloadStyleSheets(false);
        }
    }
    
    private void removeIdSilent(final String id) {
        if (this.hasId(id)) {
            this.getIds().remove(id);
        }
    }
    
    public Entry getDraggingWidget() {
        return this.draggingWidget;
    }
    
    public void registerServerMover(final ServerMover<Entry> mover) {
        this.mover = mover;
    }
    
    static {
        LIST_CONTENT = ModifyReason.of("listContent");
    }
    
    public interface ServerMover<Entry>
    {
        void move(final Entry p0, final Entry p1, final MoveActionType p2);
    }
}
