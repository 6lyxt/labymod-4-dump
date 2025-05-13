// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;

public interface Parent
{
    Parent getParent();
    
    Bounds bounds();
    
    Parent getRoot();
    
    default boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return false;
    }
}
