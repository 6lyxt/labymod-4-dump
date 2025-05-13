// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.size;

import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.RectangleState;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class WidgetSize
{
    private static final WidgetSize FIT_CONTENT;
    private static final WidgetSize MAINTAIN_OTHER;
    private static final WidgetSize ZERO;
    private final Type type;
    private final float value;
    private final boolean percentage;
    
    private WidgetSize(final Type type, final float value, final boolean percentage) {
        this.type = type;
        this.value = value;
        this.percentage = percentage;
    }
    
    public static WidgetSize fitContent() {
        return WidgetSize.FIT_CONTENT;
    }
    
    public static WidgetSize maintainOther() {
        return WidgetSize.MAINTAIN_OTHER;
    }
    
    public static WidgetSize fixed(final float value) {
        return new WidgetSize(Type.FIXED, value, false);
    }
    
    public static WidgetSize percentage(final float value) {
        return new WidgetSize(Type.FIXED, value, true);
    }
    
    public static WidgetSize zero() {
        return WidgetSize.ZERO;
    }
    
    public Type type() {
        return this.type;
    }
    
    public float value() {
        return this.value;
    }
    
    public boolean percentage() {
        return this.percentage;
    }
    
    public float computeValue(final AbstractWidget<?> widget, final WidgetSide side) {
        switch (this.type.ordinal()) {
            case 1: {
                final RectangleState state = (side == WidgetSide.WIDTH) ? RectangleState.WIDTH : RectangleState.HEIGHT;
                if (this.percentage) {
                    return this.value - widget.bounds().getOffset(BoundsType.OUTER, state);
                }
                switch (widget.boxSizing().get()) {
                    case BORDER_BOX: {
                        return this.value - widget.bounds().getOffset(BoundsType.BORDER, state);
                    }
                    case CONTENT_BOX: {
                        return this.value;
                    }
                    default: {
                        throw new IllegalStateException("Unexpected value: " + String.valueOf(widget.boxSizing().get()));
                    }
                }
                break;
            }
            case 0: {
                return widget.getContentSize(((boolean)widget.fitOuter().get()) ? BoundsType.OUTER : BoundsType.INNER, side);
            }
            case 2: {
                return (side == WidgetSide.WIDTH) ? widget.bounds().getHeight() : widget.bounds().getWidth();
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.type));
            }
        }
    }
    
    static {
        FIT_CONTENT = new WidgetSize(Type.FIT_CONTENT, -1.0f, false);
        MAINTAIN_OTHER = new WidgetSize(Type.MAINTAIN_OTHER, -1.0f, false);
        ZERO = fixed(0.0f);
    }
    
    public enum Type
    {
        FIT_CONTENT, 
        FIXED, 
        MAINTAIN_OTHER;
    }
}
