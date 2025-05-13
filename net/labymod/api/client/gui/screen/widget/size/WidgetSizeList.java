// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.size;

import net.labymod.api.Laby;
import net.labymod.api.client.render.statistics.FrameTimer;

public class WidgetSizeList
{
    private static final FrameTimer FRAME_TIMER;
    private static final SizeType[] TYPES;
    private final WidgetSize[][] sizes;
    private int lastUpdatedFrame;
    
    public WidgetSizeList() {
        this.sizes = new WidgetSize[WidgetSizeList.TYPES.length][];
        this.reset();
    }
    
    public void reset() {
        for (final SizeType type : WidgetSizeList.TYPES) {
            this.sizes[type.ordinal()] = new WidgetSize[WidgetSide.VALUES.length];
        }
    }
    
    public WidgetSize getSize(final SizeType type, final WidgetSide side) {
        return this.sizes[type.ordinal()][side.ordinal()];
    }
    
    public void setSize(final SizeType type, final WidgetSide side, final WidgetSize size) {
        this.sizes[type.ordinal()][side.ordinal()] = size;
        this.lastUpdatedFrame = WidgetSizeList.FRAME_TIMER.getFrame();
    }
    
    public boolean hasAnySize(final WidgetSide side) {
        for (final WidgetSize[] size : this.sizes) {
            if (size[side.ordinal()] != null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean wasUpdatedThisFrame() {
        return this.lastUpdatedFrame == WidgetSizeList.FRAME_TIMER.getFrame();
    }
    
    static {
        FRAME_TIMER = Laby.references().frameTimer();
        TYPES = SizeType.values();
    }
}
