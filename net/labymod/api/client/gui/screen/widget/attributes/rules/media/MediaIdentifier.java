// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes.rules.media;

import net.labymod.api.util.bounds.Rectangle;

public interface MediaIdentifier
{
    String identifier();
    
    Rectangle rectangle();
    
    void updateRectangle(final Rectangle p0);
}
