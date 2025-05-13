// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry.factory;

import java.util.zip.ZipEntry;
import java.util.function.Predicate;
import net.labymod.api.util.io.zip.entry.Entry;

public abstract class EntryFactory<T extends Entry<T>>
{
    private final Predicate<String> condition;
    
    protected EntryFactory(final Predicate<String> condition) {
        this.condition = condition;
    }
    
    public boolean shouldCreate(final String name) {
        return this.condition.test(name);
    }
    
    public abstract T create(final ZipEntry p0, final byte[] p1);
}
