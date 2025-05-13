// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class ComponentPostProcessor implements PostProcessor
{
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        if (!(element instanceof Function)) {
            return false;
        }
        final String name = ((Function)element).getName();
        return (name.equals("text") || name.equals("translatable")) && (type == String.class || type == Component.class);
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        if (!(element instanceof Function)) {
            throw new IllegalArgumentException("Element is not a function");
        }
        final Function function = (Function)element;
        final String name = function.getName();
        final ProcessedObject[] values = function.firstValues(widget, key);
        final String value = values[0].rawValue();
        if (type == String.class) {
            if (name.equals("text")) {
                return value;
            }
            if (name.equals("translatable")) {
                return I18n.translate(value, new Object[0]);
            }
        }
        else if (type == Component.class) {
            if (name.equals("text")) {
                return Component.text(value);
            }
            if (name.equals("translatable")) {
                return Component.translatable(value, new Component[0]);
            }
        }
        throw new UnsupportedOperationException("Unsupported type or function (type: " + String.valueOf(type) + ", function: " + function.getName());
    }
}
