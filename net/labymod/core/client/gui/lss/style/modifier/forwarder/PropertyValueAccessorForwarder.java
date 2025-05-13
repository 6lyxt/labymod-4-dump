// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import java.util.Collections;
import java.util.Collection;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class PropertyValueAccessorForwarder implements Forwarder
{
    private final PropertyRegistry propertyRegistry;
    
    public PropertyValueAccessorForwarder() {
        this.propertyRegistry = Laby.references().propertyRegistry();
    }
    
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return true;
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) throws Exception {
        final PropertyValueAccessor updater = this.propertyRegistry.getValueAccessor(widget, key);
        if (updater == null) {
            return;
        }
        if (object == null) {
            updater.set(widget, null);
            return;
        }
        final Object value = object.value();
        if (updater.type().isArray()) {
            updater.set(widget, updater.toArray((value instanceof Collection) ? ((Collection)value) : Collections.singletonList(value)), object);
            return;
        }
        updater.set(widget, value, object);
    }
    
    @Override
    public void forwardArray(final Widget widget, final String key, final ProcessedObject... objects) throws Exception {
        if (objects.length == 1) {
            this.forward(widget, key, objects[0]);
            return;
        }
        final PropertyValueAccessor updater = this.propertyRegistry.getValueAccessor(widget, key);
        if (updater == null) {
            return;
        }
        final int length = objects.length;
        final Object[] values = new Object[length];
        for (int i = 0; i < length; ++i) {
            values[i] = objects[i].value();
        }
        updater.set(widget, updater.toArray(values));
    }
    
    @Override
    public void reset(Widget widget, final String key) {
        final PropertyValueAccessor updater = this.getAccessor(widget, key);
        if (updater == null) {
            return;
        }
        widget = ((widget instanceof WrappedWidget) ? ((WrappedWidget)widget).childWidget() : widget);
        updater.reset(widget);
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        final PropertyValueAccessor<?, ?, ?> updater = this.getAccessor(widget, key);
        return (updater == null) ? null : updater.type();
    }
    
    @Override
    public int getPriority() {
        return 0;
    }
    
    private PropertyValueAccessor<?, ?, ?> getAccessor(final Widget widget, final String key) {
        final DirectPropertyValueAccessor directAccessor = widget.getDirectPropertyValueAccessor();
        return (directAccessor == null) ? null : directAccessor.getPropertyValueAccessor(key);
    }
}
