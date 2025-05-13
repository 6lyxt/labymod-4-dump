// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;

public interface Selector
{
    String buildSelector();
    
    boolean hasStateAttributes();
    
    boolean hasParentStateAttributes();
    
    boolean hasEnabledStateAttributes(final Widget p0);
    
    PseudoClass lastPseudoClass();
    
    boolean match(final Widget p0, final boolean p1);
    
    boolean match(final int p0, final Widget p1, final boolean p2);
}
