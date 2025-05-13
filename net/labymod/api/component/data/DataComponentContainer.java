// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.component.data;

import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public interface DataComponentContainer extends Iterable<TypedDataComponent<?>>
{
    public static final DataComponentContainer EMPTY = new DataComponentContainer() {
        @NotNull
        @Override
        public Iterator<TypedDataComponent<?>> iterator() {
            return Collections.emptyIterator();
        }
        
        @Override
        public Set<DataComponentKey> keySet() {
            return Set.of();
        }
        
        @Override
        public boolean has(final DataComponentKey key) {
            return false;
        }
        
        @Override
        public <T> T get(final DataComponentKey key) {
            return null;
        }
        
        @Override
        public <T> T getOrDefault(final DataComponentKey key, final T defaultValue) {
            return null;
        }
        
        @Override
        public <T> void set(final DataComponentKey key, final T value) {
        }
        
        @Override
        public void apply(final DataComponentPatch patch) {
        }
    };
    
    Set<DataComponentKey> keySet();
    
    boolean has(final DataComponentKey p0);
    
     <T> T get(final DataComponentKey p0);
    
     <T> T getOrDefault(final DataComponentKey p0, final T p1);
    
     <T> void set(final DataComponentKey p0, final T p1);
    
    void apply(final DataComponentPatch p0);
    
    default int size() {
        return this.keySet().size();
    }
    
    default boolean isEmpty() {
        return this.size() == 0;
    }
}
