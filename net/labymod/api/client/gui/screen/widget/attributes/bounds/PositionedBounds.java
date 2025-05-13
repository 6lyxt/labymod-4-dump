// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.Laby;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.DefaultReasonableRectangle;

public class PositionedBounds extends DefaultReasonableRectangle implements DefaultBounds
{
    private final transient WidgetStyleSheetUpdater updater;
    private final transient DefaultRectangle prevRectangle;
    private final transient ReasonableMutableRectangle[] innerRectangles;
    private transient boolean changed;
    private int lastUpdatedFrame;
    
    public PositionedBounds() {
        this((WidgetStyleSheetUpdater)null);
    }
    
    public PositionedBounds(final Bounds parent, final BoundsType type) {
        this(parent.getLeft(type), parent.getTop(type), parent.getRight(type), parent.getBottom(type));
    }
    
    public PositionedBounds(final WidgetStyleSheetUpdater updater, final Bounds parent, final BoundsType type) {
        this(updater, parent.getLeft(type), parent.getTop(type), parent.getRight(type), parent.getBottom(type));
    }
    
    public PositionedBounds(final float left, final float top, final float right, final float bottom) {
        this();
        this.setBounds(left, top, right, bottom, PositionedBounds.INIT);
    }
    
    public PositionedBounds(final WidgetStyleSheetUpdater updater, final float left, final float top, final float right, final float bottom) {
        this(updater);
        this.setBounds(left, top, right, bottom, PositionedBounds.INIT);
    }
    
    public PositionedBounds(final WidgetStyleSheetUpdater updater) {
        this.prevRectangle = new DefaultRectangle();
        this.innerRectangles = new ReasonableMutableRectangle[BoundsType.VALUES.length];
        this.changed = false;
        this.lastUpdatedFrame = -1;
        this.updater = updater;
        for (final BoundsType type : BoundsType.VALUES) {
            this.innerRectangles[type.ordinal()] = new InnerBounds(this, type);
        }
    }
    
    @Override
    public void checkForChanges() {
        if (this.changed) {
            this.changed = false;
            if (this.updater != null && !this.prevRectangle.equalsBounds(this)) {
                this.updater.onBoundsChanged(this.prevRectangle, this);
                this.prevRectangle.set(this);
            }
        }
    }
    
    private void setChanged() {
        this.changed = true;
        this.lastUpdatedFrame = Laby.references().frameTimer().getFrame();
    }
    
    @Override
    public void setLeft(final float left, final ModifyReason reason) {
        if (this.left != left) {
            this.setChanged();
            super.setLeft(left, reason);
        }
    }
    
    @Override
    public void setTop(final float top, final ModifyReason reason) {
        if (this.top != top) {
            this.setChanged();
            super.setTop(top, reason);
        }
    }
    
    @Override
    public void setRight(final float right, final ModifyReason reason) {
        if (this.right != right) {
            this.setChanged();
            super.setRight(right, reason);
        }
    }
    
    @Override
    public void setBottom(final float bottom, final ModifyReason reason) {
        if (this.bottom != bottom) {
            this.setChanged();
            super.setBottom(bottom, reason);
        }
    }
    
    @Override
    public float getOffset(final BoundsType type, final OffsetSide side) {
        if (this.updater == null) {
            return 0.0f;
        }
        if (type == BoundsType.MIDDLE) {
            return this.updater.getPadding(side);
        }
        if (type == BoundsType.BORDER) {
            return this.updater.getPadding(side) + this.updater.getBorder(side);
        }
        if (type == BoundsType.OUTER) {
            return this.updater.getPadding(side) + this.updater.getBorder(side) + this.updater.getMargin(side);
        }
        return 0.0f;
    }
    
    @Override
    public ReasonableMutableRectangle rectangle(final BoundsType type) {
        return this.innerRectangles[type.ordinal()];
    }
    
    @Override
    public WidgetStyleSheetUpdater getUpdater() {
        return this.updater;
    }
    
    @Override
    public DefaultRectangle prevRectangle() {
        return this.prevRectangle;
    }
    
    @Override
    public boolean wasUpdatedThisFrame() {
        return this.lastUpdatedFrame == Laby.references().frameTimer().getFrame();
    }
}
