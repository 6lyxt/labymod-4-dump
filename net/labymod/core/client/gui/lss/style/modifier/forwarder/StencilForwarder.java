// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class StencilForwarder implements Forwarder
{
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("stencil");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) {
        if (widget instanceof AbstractWidget) {
            final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
            abstractWidget.setStencil((boolean)object.value());
        }
    }
    
    @Override
    public void reset(final Widget widget, final String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        final AbstractWidget abstractWidget = (AbstractWidget)widget;
        abstractWidget.setStencil(false);
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return Boolean.TYPE;
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
