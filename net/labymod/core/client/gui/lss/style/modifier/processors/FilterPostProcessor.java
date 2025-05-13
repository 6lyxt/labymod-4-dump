// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import java.util.HashMap;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.screen.widget.attributes.FilterType;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class FilterPostProcessor implements PostProcessor
{
    private static final Map<String, FilterType> FUNCTIONS;
    
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        if (element instanceof final Function function) {
            return FilterPostProcessor.FUNCTIONS.get(function.getName()) != null;
        }
        return FilterPostProcessor.FUNCTIONS.get(element.getRawValue()) != null;
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        if (element instanceof final Function function) {
            final ProcessedObject[] values = function.firstValues(widget, key);
            return new Filter(FilterPostProcessor.FUNCTIONS.get(function.getName()), values);
        }
        return new Filter(FilterPostProcessor.FUNCTIONS.get(element.getRawValue()), (ProcessedObject[])null);
    }
    
    @Override
    public int getPriority() {
        return 2;
    }
    
    static {
        FUNCTIONS = new HashMap<String, FilterType>();
        for (final FilterType type : FilterType.values()) {
            FilterPostProcessor.FUNCTIONS.put(type.getName(), type);
        }
    }
}
