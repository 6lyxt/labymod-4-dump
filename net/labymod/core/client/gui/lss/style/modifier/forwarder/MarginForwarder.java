// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class MarginForwarder implements Forwarder
{
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("margin");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) {
        if (widget instanceof AbstractWidget) {
            final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
            final Object value = object.value();
            if (value instanceof final String string) {
                final String[] sides = string.split(" ");
                if (sides.length > 0) {
                    abstractWidget.marginTop().set(Float.parseFloat(sides[0]));
                }
                if (sides.length > 1) {
                    abstractWidget.marginRight().set(Float.parseFloat(sides[1]));
                }
                if (sides.length > 2) {
                    abstractWidget.marginBottom().set(Float.parseFloat(sides[2]));
                }
                if (sides.length > 3) {
                    abstractWidget.marginLeft().set(Float.parseFloat(sides[3]));
                }
            }
            else {
                final float padding = (float)value;
                abstractWidget.marginLeft().set(padding);
                abstractWidget.marginTop().set(padding);
                abstractWidget.marginRight().set(padding);
                abstractWidget.marginBottom().set(padding);
            }
        }
    }
    
    @Override
    public void reset(final Widget widget, final String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        final AbstractWidget abstractWidget = (AbstractWidget)widget;
        abstractWidget.marginLeft().reset();
        abstractWidget.marginTop().reset();
        abstractWidget.marginRight().reset();
        abstractWidget.marginBottom().reset();
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return (Class<?>)(value.contains(" ") ? String.class : Float.TYPE);
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
