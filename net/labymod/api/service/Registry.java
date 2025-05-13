// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.service;

import net.labymod.api.util.function.Consumers;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.util.KeyValue;
import java.util.List;

public interface Registry<T>
{
    List<KeyValue<T>> getElements();
    
    default List<T> values() {
        final List<T> values = new ArrayList<T>();
        for (final KeyValue<T> element : this.getElements()) {
            values.add(element.getValue());
        }
        return values;
    }
    
    default void register(final List<? extends T> elements) {
        for (final T element : elements) {
            this.register(element);
        }
    }
    
    default void register(final T element) {
        String key = element.toString();
        if (element instanceof final Identifiable identifiable) {
            key = identifiable.getId();
        }
        this.register(key, element);
    }
    
    default void register(final Supplier<T> elementSupplier, final Consumer<Throwable> errorHandler) {
        try {
            this.register(elementSupplier.get());
        }
        catch (final Throwable throwable) {
            errorHandler.accept(throwable);
        }
    }
    
    default void register(final String id, final T element) {
        this.getElements().add(new KeyValue<T>(id, element));
    }
    
    default T getOrRegister(final String id, final T element) {
        final KeyValue<T> value = this.getElementById(id);
        if (value == null) {
            this.register(id, element);
            return element;
        }
        return value.getValue();
    }
    
    default void registerAfter(final String afterId, final T element) {
        String id = element.toString();
        if (element instanceof final Identifiable identifiable) {
            id = identifiable.getId();
        }
        this.registerAfter(afterId, id, element);
    }
    
    default void registerAfter(final String afterId, final String id, final T element) {
        this.getElements().add(this.indexOf(afterId) + 1, new KeyValue<T>(id, element));
    }
    
    default T getOrRegisterAfter(final String afterId, final String id, final T element) {
        final KeyValue<T> value = this.getElementById(id);
        if (value == null) {
            this.registerAfter(afterId, id, element);
            return element;
        }
        return value.getValue();
    }
    
    default void registerBefore(final String beforeId, final T element) {
        String id = element.toString();
        if (element instanceof final Identifiable identifiable) {
            id = identifiable.getId();
        }
        this.registerBefore(beforeId, id, element);
    }
    
    default void registerBefore(final String beforeId, final String id, final T element) {
        this.getElements().add(this.indexOf(beforeId), new KeyValue<T>(id, element));
    }
    
    default T getOrRegisterBefore(final String beforeId, final String id, final T element) {
        final KeyValue<T> value = this.getElementById(id);
        if (value == null) {
            this.registerBefore(beforeId, id, element);
            return element;
        }
        return value.getValue();
    }
    
    default void unregister(final String id) {
        final KeyValue<T> element = this.getElementById(id);
        if (element != null) {
            this.getElements().remove(element);
        }
    }
    
    default void unregister(final Predicate<KeyValue<T>> predicate) {
        this.getElements().removeIf(predicate);
    }
    
    default void unregisterByClassLoader(final ClassLoader classLoader) {
        this.getElements().removeIf(entry -> entry.getValue().getClass().getClassLoader().equals(classLoader));
    }
    
    default int indexOf(final String id) {
        final KeyValue<T> element = this.getElementById(id);
        return (element == null) ? 0 : this.getElements().indexOf(element);
    }
    
    default int indexOf(final T value) {
        return (value == null) ? 0 : this.values().indexOf(value);
    }
    
    @Nullable
    default KeyValue<T> getElementById(final String id) {
        for (final KeyValue<T> element : this.getElements()) {
            if (element.getKey().equals(id)) {
                return element;
            }
        }
        return null;
    }
    
    default T getById(final String id) {
        final KeyValue<T> element = this.getElementById(id);
        return (element == null) ? null : element.getValue();
    }
    
    default Optional<T> getOptionalById(final String id) {
        for (final KeyValue<T> element : this.getElements()) {
            if (element.getKey().equals(id)) {
                return Optional.of(element.getValue());
            }
        }
        return Optional.empty();
    }
    
    default void forEach(final Consumer<T> consumer) {
        for (final KeyValue<T> element : this.getElements()) {
            Consumers.accept(consumer, element.getValue());
        }
    }
    
    default String getId(final T target) {
        for (final KeyValue<T> element : this.getElements()) {
            if (element.getValue() == target) {
                return element.getKey();
            }
        }
        return null;
    }
}
