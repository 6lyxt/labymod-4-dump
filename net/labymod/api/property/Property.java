// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.property;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.Objects;
import java.util.ArrayList;
import net.labymod.api.util.function.ChangeListener;
import java.util.List;

public class Property<T>
{
    protected final List<ChangeListener<Property<T>, T>> changeListeners;
    protected T value;
    protected T defaultValue;
    protected PropertyConvention<T> propertyConvention;
    
    public Property(final T value) {
        this(value, null);
    }
    
    public Property(final T value, final PropertyConvention<T> propertyConvention) {
        this.changeListeners = new ArrayList<ChangeListener<Property<T>, T>>();
        this.value = value;
        this.propertyConvention = propertyConvention;
        this.defaultValue = value;
    }
    
    public <P extends Property<T>> P addChangeListener(final ChangeListener<Property<T>, T> listener) {
        Objects.requireNonNull(listener, "listener must not be null");
        this.changeListeners.add(listener);
        return (P)this;
    }
    
    public <P extends Property<T>> P addChangeListener(final Consumer<T> listener) {
        Objects.requireNonNull(listener, "listener must not be null");
        this.changeListeners.add((type, oldValue, newValue) -> listener.accept(newValue));
        return (P)this;
    }
    
    public <P extends Property<T>> P addChangeListener(final Runnable listener) {
        Objects.requireNonNull(listener, "listener must not be null");
        this.changeListeners.add((type, oldValue, newValue) -> listener.run());
        return (P)this;
    }
    
    public void set(T value) {
        final T prevValue = this.value;
        final boolean changed = !Objects.equals(prevValue, value);
        if (changed) {
            value = this.applyConvention(value);
        }
        this.value = value;
        if (changed) {
            for (final ChangeListener<Property<T>, T> listener : this.changeListeners) {
                listener.changed(this, prevValue, value);
            }
        }
    }
    
    public T get() {
        return this.value;
    }
    
    public T getOrDefault() {
        return this.getOrDefault(this.defaultValue);
    }
    
    public T getOrDefault(final T defaultValue) {
        return (this.value == null) ? defaultValue : this.value;
    }
    
    public T defaultValue() {
        return this.defaultValue;
    }
    
    public void reset() {
        this.set(this.defaultValue);
    }
    
    public void updateDefaultValue(final T newDefaultValue) {
        this.defaultValue = newDefaultValue;
    }
    
    public boolean isDefaultValue(final T value) {
        return Objects.equals(this.defaultValue, value);
    }
    
    public boolean isDefaultValue() {
        return this.isDefaultValue(this.value);
    }
    
    protected T applyConvention(final T value) {
        return (this.propertyConvention == null) ? value : this.propertyConvention.convention(value);
    }
}
