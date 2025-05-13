// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border;

import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.AlignmentLine;

public abstract class RangeAlignmentLine extends AlignmentLine
{
    protected final float range;
    
    public RangeAlignmentLine(final float range) {
        this.range = range;
    }
    
    public boolean isInRange(final Rectangle chain) {
        return chain != null && Math.abs(this.getDistance(chain)) < this.range;
    }
    
    protected abstract float getDistance(final Rectangle p0);
    
    public abstract void align(final MutableRectangle p0);
}
