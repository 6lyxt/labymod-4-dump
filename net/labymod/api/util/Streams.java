// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import org.jetbrains.annotations.NotNull;
import java.util.stream.StreamSupport;
import java.util.Collection;
import java.util.stream.Stream;

public final class Streams
{
    private Streams() {
    }
    
    @NotNull
    public static <T> Stream<T> stream(final Iterable<T> iterable) {
        return (iterable instanceof Collection) ? ((Collection)iterable).stream() : StreamSupport.stream(iterable.spliterator(), false);
    }
}
