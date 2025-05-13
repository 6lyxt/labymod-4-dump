// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import net.labymod.api.util.bounds.RectangleModification;
import net.labymod.api.util.bounds.RectangleState;
import java.util.Map;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;

class InnerBounds implements ReasonableMutableRectangle
{
    private final Bounds bounds;
    private final BoundsType type;
    
    InnerBounds(final Bounds bounds, final BoundsType type) {
        this.bounds = bounds;
        this.type = type;
    }
    
    @Override
    public float getLeft() {
        return this.bounds.getLeft(this.type);
    }
    
    @Override
    public float getTop() {
        return this.bounds.getTop(this.type);
    }
    
    @Override
    public float getRight() {
        return this.bounds.getRight(this.type);
    }
    
    @Override
    public float getBottom() {
        return this.bounds.getBottom(this.type);
    }
    
    @Override
    public String toString() {
        return this.getLeft() + "," + this.getTop() + " (" + (this.getRight() - this.getLeft()) + "x" + (this.getBottom() - this.getTop());
    }
    
    @Override
    public void setLeft(final float left, final ModifyReason reason) {
        this.bounds.setLeft(left, this.type, reason);
    }
    
    @Override
    public void setTop(final float top, final ModifyReason reason) {
        this.bounds.setTop(top, this.type, reason);
    }
    
    @Override
    public void setRight(final float right, final ModifyReason reason) {
        this.bounds.setRight(right, this.type, reason);
    }
    
    @Override
    public void setBottom(final float bottom, final ModifyReason reason) {
        this.bounds.setBottom(bottom, this.type, reason);
    }
    
    @Override
    public void recordModifications() {
        this.bounds.recordModifications();
    }
    
    @Override
    public void stopRecordingModifications() {
        this.bounds.stopRecordingModifications();
    }
    
    @NotNull
    @Override
    public Map<RectangleState, RectangleModification> lastModifications() {
        final Map<RectangleState, RectangleModification> base = this.bounds.lastModifications();
        if (base.isEmpty()) {
            return base;
        }
        final Map<RectangleState, RectangleModification> innerMods = new HashMap<RectangleState, RectangleModification>(base.size());
        base.forEach((state, mod) -> {
            float offset = this.bounds.getOffset(this.type, state);
            if (state == RectangleState.BOTTOM || state == RectangleState.RIGHT) {
                offset = -offset;
            }
            innerMods.put(state, mod.withOffset(offset, offset));
            return;
        });
        return innerMods;
    }
}
