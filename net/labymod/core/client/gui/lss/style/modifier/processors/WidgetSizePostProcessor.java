// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class WidgetSizePostProcessor implements PostProcessor
{
    public final WidgetModifier modifier;
    
    public WidgetSizePostProcessor(final WidgetModifier modifier) {
        this.modifier = modifier;
    }
    
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        return type == WidgetSize.class;
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        final String value = element.getRawValue();
        if (value.equals("fit-content")) {
            return WidgetSize.fitContent();
        }
        if (value.equals("maintain-other")) {
            return WidgetSize.maintainOther();
        }
        final ProcessedObject object = this.modifier.processValue(widget, Float.TYPE, key, element)[0];
        final float result = (float)object.value();
        return (object.postProcessor() instanceof PercentagePostProcessor) ? WidgetSize.percentage(result) : WidgetSize.fixed(result);
    }
}
