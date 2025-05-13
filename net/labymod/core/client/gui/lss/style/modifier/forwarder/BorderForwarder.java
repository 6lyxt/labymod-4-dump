// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.screen.widget.attributes.Border;
import net.labymod.core.client.gui.lss.style.modifier.processors.BorderPostProcessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class BorderForwarder implements Forwarder
{
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("border");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) throws Exception {
        if (widget instanceof AbstractWidget) {
            final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
            final BorderPostProcessor.Border border = (BorderPostProcessor.Border)object.value();
            if (!abstractWidget.hasBorder()) {
                abstractWidget.border = new Border();
            }
            abstractWidget.border.setColor(border.color);
            abstractWidget.border.setWidth((float)border.leftWidth, (float)border.topWidth, (float)border.rightWidth, (float)border.bottomWidth);
        }
    }
    
    @Override
    public void reset(final Widget widget, final String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        final AbstractWidget abstractWidget = (AbstractWidget)widget;
        if (abstractWidget.border == null) {
            return;
        }
        abstractWidget.border.setColor(0);
        abstractWidget.border.setWidth(0.0f);
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return BorderPostProcessor.Border.class;
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
