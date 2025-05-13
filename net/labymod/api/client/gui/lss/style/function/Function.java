// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.function;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;

public interface Function extends Element
{
    String getName();
    
    Element[] parameters();
    
    ProcessedObject[][] allValues(final Widget p0, final String p1) throws Exception;
    
    ProcessedObject[] firstValues(final Widget p0, final String p1) throws Exception;
    
    String toString();
}
