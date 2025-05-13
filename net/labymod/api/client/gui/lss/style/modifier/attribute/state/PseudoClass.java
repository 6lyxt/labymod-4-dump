// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute.state;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;

public interface PseudoClass
{
    Element element();
    
    boolean matchesState(final Widget p0);
    
    int getPriority();
}
