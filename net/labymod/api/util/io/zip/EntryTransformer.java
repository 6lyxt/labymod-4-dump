// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip;

import java.util.function.Predicate;
import net.labymod.api.util.io.zip.entry.Entry;

public abstract class EntryTransformer<T extends Entry<?>>
{
    private final Predicate<Entry<?>> processPredicate;
    
    public EntryTransformer(final Predicate<Entry<?>> processPredicate) {
        this.processPredicate = processPredicate;
    }
    
    public boolean canProcess(final T entry) {
        return this.processPredicate.test(entry);
    }
    
    public abstract T process(final T p0);
}
