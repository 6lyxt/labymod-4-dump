// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

import java.util.concurrent.TimeUnit;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.HashMap;
import net.labymod.api.util.time.TimeUtil;
import java.util.Map;

public class DefaultReasonableRectangle implements ReasonableMutableRectangle
{
    private static final long MODIFICATION_TIMEOUT;
    protected static final ModifyReason INIT;
    protected float left;
    protected float top;
    protected float right;
    protected float bottom;
    private Map<RectangleState, RectangleModification> recordedModifications;
    
    public DefaultReasonableRectangle() {
    }
    
    public DefaultReasonableRectangle(final Rectangle rectangle) {
        this.set(rectangle, DefaultReasonableRectangle.INIT);
    }
    
    protected DefaultReasonableRectangle(final float left, final float top, final float right, final float bottom) {
        this.setBounds(left, top, right, bottom, DefaultReasonableRectangle.INIT);
    }
    
    @Override
    public void setLeft(final float left, final ModifyReason reason) {
        if (this.recordedModifications != null) {
            this.record(RectangleState.LEFT, this.left, left, reason);
        }
        this.left = left;
    }
    
    @Override
    public void setTop(final float top, final ModifyReason reason) {
        if (this.recordedModifications != null) {
            this.record(RectangleState.TOP, this.top, top, reason);
        }
        this.top = top;
    }
    
    @Override
    public void setRight(final float right, final ModifyReason reason) {
        if (this.recordedModifications != null) {
            this.record(RectangleState.RIGHT, this.right, right, reason);
        }
        this.right = right;
    }
    
    @Override
    public void setBottom(final float bottom, final ModifyReason reason) {
        if (this.recordedModifications != null) {
            this.record(RectangleState.BOTTOM, this.bottom, bottom, reason);
        }
        this.bottom = bottom;
    }
    
    @Override
    public void setWidth(final float width, final ModifyReason reason) {
        if (this.recordedModifications != null && this.getWidth() != width) {
            this.record(RectangleState.WIDTH, this.getWidth(), width, reason);
        }
        super.setWidth(width, reason);
    }
    
    @Override
    public void setHeight(final float height, final ModifyReason reason) {
        if (this.recordedModifications != null && this.getHeight() != height) {
            this.record(RectangleState.HEIGHT, this.getHeight(), height, reason);
        }
        super.setHeight(height, reason);
    }
    
    private void record(final RectangleState state, final float from, final float to, final ModifyReason reason) {
        this.record(new RectangleModification(state, from, to, reason, TimeUtil.getMillis(), this.recordedModifications.get(state)));
    }
    
    private void record(final RectangleModification modification) {
        this.recordedModifications.put(modification.state(), modification);
    }
    
    @Override
    public void recordModifications() {
        if (this.recordedModifications == null) {
            this.recordedModifications = new HashMap<RectangleState, RectangleModification>();
        }
    }
    
    @Override
    public void stopRecordingModifications() {
        this.recordedModifications = null;
    }
    
    @NotNull
    @Override
    public Map<RectangleState, RectangleModification> lastModifications() {
        if (this.recordedModifications == null) {
            return Collections.emptyMap();
        }
        this.recordedModifications.values().removeIf(mod -> TimeUtil.getMillis() - mod.timestamp() > DefaultReasonableRectangle.MODIFICATION_TIMEOUT);
        return this.recordedModifications;
    }
    
    @Override
    public float getLeft() {
        return this.left;
    }
    
    @Override
    public float getTop() {
        return this.top;
    }
    
    @Override
    public float getRight() {
        return this.right;
    }
    
    @Override
    public float getBottom() {
        return this.bottom;
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s,%s (%sx%s)", this.left, this.top, this.getWidth(), this.getHeight());
    }
    
    static {
        MODIFICATION_TIMEOUT = TimeUnit.SECONDS.toMillis(15L);
        INIT = ModifyReason.of("init");
    }
}
