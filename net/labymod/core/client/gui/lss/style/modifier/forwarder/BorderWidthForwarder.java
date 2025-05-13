// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.screen.widget.attributes.Border;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class BorderWidthForwarder implements Forwarder
{
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("borderWidth");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) throws Exception {
        if (widget instanceof AbstractWidget) {
            final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
            final float width = (float)object.value();
            if (!abstractWidget.hasBorder()) {
                abstractWidget.border = new Border();
            }
            abstractWidget.border.setWidth(width);
        }
    }
    
    @Override
    public void reset(final Widget widget, final String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        final AbstractWidget abstractWidget = (AbstractWidget)widget;
        abstractWidget.border = null;
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return Float.TYPE;
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
