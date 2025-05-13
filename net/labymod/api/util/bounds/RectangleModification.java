// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.DefaultBounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.function.Function;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class RectangleModification
{
    private static final StackWalker WALKER;
    private static final List<String> STACKTRACE_EXCLUSIONS;
    private final RectangleState state;
    private final float from;
    private final float to;
    private final ModifyReason reason;
    private final long timestamp;
    private final RectangleModification previousModification;
    private final int frame;
    private final StackTraceElement[] stackTrace;
    
    public RectangleModification(@NotNull final RectangleState state, final float from, final float to, @NotNull final ModifyReason reason, final long timestamp, @Nullable final RectangleModification previousModification) {
        this.state = state;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.timestamp = timestamp;
        this.previousModification = previousModification;
        this.frame = Laby.references().frameTimer().getFrame();
        this.stackTrace = (StackTraceElement[])RectangleModification.WALKER.walk(stream -> stream.map(StackWalker.StackFrame::toStackTraceElement).toList()).toArray(StackTraceElement[]::new);
    }
    
    @NotNull
    public RectangleState state() {
        return this.state;
    }
    
    public float from() {
        return this.from;
    }
    
    public float to() {
        return this.to;
    }
    
    @NotNull
    public ModifyReason reason() {
        return this.reason;
    }
    
    public long timestamp() {
        return this.timestamp;
    }
    
    @Nullable
    public RectangleModification getPreviousModification() {
        return this.previousModification;
    }
    
    public int frame() {
        return this.frame;
    }
    
    @NotNull
    public StackTraceElement[] stackTrace() {
        return this.stackTrace;
    }
    
    public RectangleModification withOffset(final float xOffset, final float yOffset) {
        return this.withOffset(this, xOffset, yOffset);
    }
    
    private RectangleModification withOffset(final RectangleModification mod, final float xOffset, final float yOffset) {
        if (mod == null) {
            return null;
        }
        return new RectangleModification(mod.state(), mod.from() + xOffset, mod.to() + yOffset, mod.reason(), mod.timestamp(), this.withOffset(mod.getPreviousModification(), xOffset, yOffset));
    }
    
    @NotNull
    public StackTraceElement lastExternalTrace() {
        for (final StackTraceElement stackTraceElement : this.stackTrace) {
            if (!RectangleModification.STACKTRACE_EXCLUSIONS.contains(stackTraceElement.getClassName())) {
                return stackTraceElement;
            }
        }
        throw new IllegalArgumentException("No external stacktrace element found");
    }
    
    @NotNull
    public StackTraceElement[] externalStackTrace() {
        for (int i = 0; i < this.stackTrace.length; ++i) {
            if (!RectangleModification.STACKTRACE_EXCLUSIONS.contains(this.stackTrace[i].getClassName())) {
                return Arrays.copyOfRange(this.stackTrace, i, this.stackTrace.length - 1);
            }
        }
        throw new IllegalArgumentException("No external stacktrace element found");
    }
    
    @Override
    public String toString() {
        return "RectangleModification{" + this.from + " -> " + this.to + " @ " + this.reason.reason();
    }
    
    static {
        WALKER = StackWalker.getInstance();
        STACKTRACE_EXCLUSIONS = Arrays.asList(Thread.class.getName(), RectangleModification.class.getName(), Bounds.class.getName(), PositionedBounds.class.getName(), DefaultBounds.class.getName(), "net.labymod.api.client.gui.screen.widget.attributes.bounds.InnerBounds", DefaultReasonableRectangle.class.getName(), ReasonableMutableRectangle.class.getName());
    }
}
