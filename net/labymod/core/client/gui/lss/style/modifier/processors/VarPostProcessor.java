// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.SingleFunctionPostProcessor;

public class VarPostProcessor extends SingleFunctionPostProcessor
{
    private final WidgetModifier modifier;
    
    public VarPostProcessor(final WidgetModifier modifier) {
        super("var");
        this.modifier = modifier;
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Function function) throws Exception {
        final String var = (String)function.firstValues(widget, key)[0].value();
        final LssVariable variable = widget.getVariable(var);
        return (variable != null) ? this.modifier.processValue(widget, type, key, variable.value()) : null;
    }
}
