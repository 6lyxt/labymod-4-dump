// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.collection;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;

public final class Lists
{
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }
    
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(final T... entries) {
        final ArrayList<T> list = new ArrayList<T>(entries.length);
        Collections.addAll(list, entries);
        return list;
    }
    
    public static <T> ArrayList<T> newArrayList(final Collection<T> collection) {
        return new ArrayList<T>((Collection<? extends T>)collection);
    }
    
    @NotNull
    public static <T> ArrayList<T> newDistinctArrayList(@NotNull final Collection<T> collection, final boolean originalOrder) {
        final Set<T> set = originalOrder ? new LinkedHashSet<T>((Collection<? extends T>)collection) : new HashSet<T>((Collection<? extends T>)collection);
        return new ArrayList<T>((Collection<? extends T>)set);
    }
}
