// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.service;

import java.util.function.Predicate;
import java.util.ArrayList;
import net.labymod.api.util.KeyValue;
import java.util.List;

public class DefaultRegistry<T> implements Registry<T>
{
    private final transient List<KeyValue<T>> elements;
    
    public DefaultRegistry() {
        this.elements = new ArrayList<KeyValue<T>>();
    }
    
    @Override
    public List<KeyValue<T>> getElements() {
        return this.elements;
    }
    
    @Override
    public void register(final String id, final T element) {
        final KeyValue<T> value = new KeyValue<T>(id, element);
        this.getElements().add(value);
        this.onRegister(value);
    }
    
    @Override
    public void registerBefore(final String beforeId, final String id, final T element) {
        final KeyValue<T> value = new KeyValue<T>(id, element);
        this.getElements().add(this.indexOf(beforeId), value);
        this.onRegister(value);
    }
    
    @Override
    public void registerAfter(final String afterId, final String id, final T element) {
        final KeyValue<T> value = new KeyValue<T>(id, element);
        this.getElements().add(this.indexOf(afterId) + 1, value);
        this.onRegister(value);
    }
    
    @Override
    public void unregister(final String id) {
        final KeyValue<T> element = this.getElementById(id);
        super.unregister(id);
        if (element != null) {
            this.onUnregister(element);
        }
    }
    
    @Override
    public void unregister(final Predicate<KeyValue<T>> predicate) {
        this.getElements().removeIf(value -> {
            if (predicate.test(value)) {
                this.onUnregister(value);
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    @Override
    public void unregisterByClassLoader(final ClassLoader classLoader) {
        this.getElements().removeIf(entry -> {
            if (entry.getValue().getClass().getClassLoader().equals(classLoader)) {
                this.onUnregister(entry);
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    protected void onRegister(final KeyValue<T> value) {
    }
    
    protected void onUnregister(final KeyValue<T> value) {
    }
}
