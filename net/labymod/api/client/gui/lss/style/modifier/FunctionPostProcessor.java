// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Function;

public interface FunctionPostProcessor extends PostProcessor
{
    boolean requiresPostProcessing(final String p0, final Function p1, final Class<?> p2);
    
    Object process(final Widget p0, final Class<?> p1, final String p2, final Function p3) throws Exception;
    
    default boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        return element instanceof Function && this.requiresPostProcessing(key, (Function)element, type);
    }
    
    default Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        return this.process(widget, type, key, (Function)element);
    }
}
