// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry.factory;

import net.labymod.api.util.io.zip.entry.Entry;
import java.util.zip.ZipEntry;
import java.util.Objects;
import net.labymod.api.util.io.zip.entry.ManifestEntry;

public class ManifestEntryFactory extends EntryFactory<ManifestEntry>
{
    public ManifestEntryFactory() {
        super(name -> Objects.equals(name, "META-INF/MANIFEST.MF"));
    }
    
    @Override
    public ManifestEntry create(final ZipEntry entry, final byte[] data) {
        return new ManifestEntry(entry.getTime(), data);
    }
}
