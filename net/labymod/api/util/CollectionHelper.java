// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Array;
import org.jetbrains.annotations.NotNull;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.Locale;
import org.spongepowered.include.com.google.common.collect.Sets;
import java.util.Set;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.function.Supplier;

public class CollectionHelper
{
    private static final Supplier<Collection<?>> ARRAY_LIST_SUPPLIER;
    
    private CollectionHelper() {
    }
    
    public static <T> List<T> asUnmodifiableList(final T... elements) {
        return Collections.unmodifiableList((List<? extends T>)Arrays.asList((T[])elements));
    }
    
    public static <T> Set<T> asUnmodifiableSet(final T... elements) {
        return Collections.unmodifiableSet((Set<? extends T>)Sets.newHashSet((Object[])elements));
    }
    
    public static <T> void copyOfRange(final T[] source, final T[] destination, final int from, final int to) {
        final int length = to - from;
        if (length < 0) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Provided range has negative length (from: %d, to: %d, length: %d)", from, to, length));
        }
        System.arraycopy(source, from, destination, 0, length);
    }
    
    public static <T> boolean contains(final T[] array, final T filter) {
        for (final T t : array) {
            if (Objects.equals(t, filter)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean containsNonNull(final T[] array) {
        for (final T t : array) {
            if (t != null) {
                return true;
            }
        }
        return false;
    }
    
    @Nullable
    public static <T> T get(final Collection<T> collection, final Predicate<T> predicate) {
        return get(collection, predicate, (T)null);
    }
    
    public static <T, R> R[] mapArray(final T[] array, final Class<R> resultClass, @NotNull final Function<T, R> mapFunction) {
        final R[] resultArray = (R[])Array.newInstance(resultClass, array.length);
        for (int i = 0; i < array.length; ++i) {
            resultArray[i] = mapFunction.apply(array[i]);
        }
        return resultArray;
    }
    
    @NotNull
    public static <T, R> Collection<R> map(final T[] array, @NotNull final Function<T, R> mapFunction) {
        return map(Arrays.asList(array), mapFunction);
    }
    
    @NotNull
    public static <T, R, C extends Collection<T>> Collection<R> map(@NotNull final C collection, @NotNull final Function<T, R> mapFunction) {
        Objects.requireNonNull(mapFunction, "mapFunction must not be null");
        final Collection<R> mappedCollection = new ArrayList<R>();
        for (final T t : collection) {
            mappedCollection.add(mapFunction.apply(t));
        }
        return mappedCollection;
    }
    
    public static <T> T get(final Collection<T> collection, final Predicate<T> predicate, final T defaultValue) {
        for (final T entry : collection) {
            if (predicate.test(entry)) {
                return entry;
            }
        }
        return defaultValue;
    }
    
    @Nullable
    public static <T> T removeFirstIf(final Collection<T> collection, final Predicate<T> predicate) {
        final T target = (T)get((Collection<Object>)collection, (Predicate<Object>)predicate);
        if (target != null) {
            collection.remove(target);
        }
        return target;
    }
    
    public static <T> boolean removeIf(final Collection<T> collection, final Predicate<T> predicate) {
        boolean match = false;
        final Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final T entry = iterator.next();
            if (predicate.test(entry)) {
                iterator.remove();
                match = true;
            }
        }
        return match;
    }
    
    public static <T> boolean anyMatch(final Collection<T> entries, final Predicate<T> predicate) {
        for (final T entry : entries) {
            if (predicate.test(entry)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean anyMatch(final T[] entries, final Predicate<T> predicate) {
        for (final T entry : entries) {
            if (predicate.test(entry)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean allMatch(final Collection<T> entries, final Predicate<T> predicate) {
        for (final T entry : entries) {
            if (!predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean allMatch(final T[] entries, final Predicate<T> predicate) {
        for (final T entry : entries) {
            if (!predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean noneMatch(final Collection<T> entries, final Predicate<T> predicate) {
        for (final T entry : entries) {
            if (predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean noneMatch(final T[] entries, final Predicate<T> predicate) {
        for (final T entry : entries) {
            if (predicate.test(entry)) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> int indexOf(final List<T> entries, final Predicate<T> predicate) {
        for (int i = 0; i < entries.size(); ++i) {
            if (predicate.test(entries.get(i))) {
                return i;
            }
        }
        return -1;
    }
    
    public static <T> Collection<T> filter(final Collection<T> collection, final Predicate<T> filter) {
        return filter(collection, (Supplier<Collection<T>>)CollectionHelper.ARRAY_LIST_SUPPLIER, filter);
    }
    
    public static <L extends Collection<T>, T> L filter(final L collection, final Supplier<L> supplier, final Predicate<T> filter) {
        final L result = supplier.get();
        for (final T t : collection) {
            if (filter.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
    
    static {
        ARRAY_LIST_SUPPLIER = ArrayList::new;
    }
}
