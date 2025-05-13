// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.property;

import java.util.Collection;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;

public interface PropertyValueAccessor<W extends Widget, V, A>
{
    default void set(final W widget, final V value) {
        this.getProperty(widget).set(value);
    }
    
    default void set(final W widget, final V value, final ProcessedObject processedValue) {
        this.getProperty(widget).set(value, processedValue);
    }
    
    default void reset(final W widget) {
        this.getProperty(widget).reset();
    }
    
    default V get(final W widget) {
        return this.getProperty(widget).get();
    }
    
    LssProperty<V> getProperty(final W p0);
    
    default Class<?> type() {
        return null;
    }
    
    A[] toArray(final Object[] p0);
    
    A[] toArray(final Collection<A> p0);
}
