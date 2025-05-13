// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry.factory;

import net.labymod.api.util.io.zip.entry.Entry;
import java.util.zip.ZipEntry;
import net.labymod.api.util.io.zip.entry.ResourceEntry;

public class ResourceEntryFactory extends EntryFactory<ResourceEntry>
{
    public ResourceEntryFactory() {
        super(name -> true);
    }
    
    @Override
    public ResourceEntry create(final ZipEntry entry, final byte[] data) {
        return new ResourceEntry(entry.getName(), entry.getTime(), data);
    }
}
