// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.core.client.gui.lss.style.modifier.processors.PercentagePostProcessor;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class WidthHeightForwarder implements Forwarder
{
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("width") || key.equals("height");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) {
        final WidgetSide side = key.equals("width") ? WidgetSide.WIDTH : WidgetSide.HEIGHT;
        WidgetSize size = null;
        final Object value = object.value();
        if (value instanceof Float) {
            if (object.postProcessor() instanceof PercentagePostProcessor) {
                size = WidgetSize.percentage((float)value);
            }
            else {
                size = WidgetSize.fixed((float)value);
            }
        }
        else if (value instanceof final String mode) {
            if (mode.equals("fit-content")) {
                size = WidgetSize.fitContent();
            }
            else if (mode.equals((side == WidgetSide.WIDTH) ? "height" : "width")) {
                size = WidgetSize.maintainOther();
            }
        }
        if (size != null) {
            widget.setSize(SizeType.ACTUAL, side, size);
        }
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return (Class<?>)((value.equals("fit-content") || (key.equals("width") ? value.equals("height") : value.equals("width"))) ? String.class : Float.class);
    }
    
    @Override
    public int getPriority() {
        return 2;
    }
}
