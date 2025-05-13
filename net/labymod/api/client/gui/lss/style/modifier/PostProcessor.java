// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface PostProcessor
{
    boolean requiresPostProcessing(final String p0, final Element p1, final Class<?> p2);
    
    Object process(final Widget p0, final Class<?> p1, final String p2, final Element p3) throws Exception;
    
    default int getPriority() {
        return 0;
    }
}
