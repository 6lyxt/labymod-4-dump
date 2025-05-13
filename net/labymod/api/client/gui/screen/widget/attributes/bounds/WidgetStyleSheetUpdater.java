// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.util.bounds.Rectangle;

public interface WidgetStyleSheetUpdater
{
    void onBoundsChanged(final Rectangle p0, final Rectangle p1);
    
    Float getPadding(final OffsetSide p0);
    
    Float getBorder(final OffsetSide p0);
    
    Float getMargin(final OffsetSide p0);
    
    boolean isUsingFloatingPointPosition();
}
