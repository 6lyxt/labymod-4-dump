// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.util.math.LssMath;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.FunctionPostProcessor;

public class MathPostProcessor implements FunctionPostProcessor
{
    @Override
    public boolean requiresPostProcessing(final String key, final Function function, final Class<?> type) {
        return function.getName().equals("min") || function.getName().equals("max") || function.getName().equals("calc");
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Function function) throws Exception {
        final String name = function.getName();
        switch (name) {
            case "min": {
                return LssMath.min(widget, key, function.firstValues(widget, key));
            }
            case "max": {
                return LssMath.max(widget, key, function.firstValues(widget, key));
            }
            case "calc": {
                final StringBuilder builder = new StringBuilder();
                for (final ProcessedObject[] array : function.allValues(widget, key)) {
                    final ProcessedObject[] values = array;
                    for (final ProcessedObject value : array) {
                        builder.append(value.value());
                    }
                }
                return LssMath.calc(widget, key, builder.toString());
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + function.getName());
            }
        }
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
