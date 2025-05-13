// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry.factory;

import net.labymod.api.util.io.zip.entry.Entry;
import java.util.zip.ZipEntry;
import net.labymod.api.util.io.zip.entry.ClassEntry;

public class ClassEntryFactory extends EntryFactory<ClassEntry>
{
    public ClassEntryFactory() {
        super(name -> name.endsWith(".class"));
    }
    
    @Override
    public ClassEntry create(final ZipEntry entry, final byte[] data) {
        return new ClassEntry(entry.getName(), entry.getTime(), data);
    }
}
