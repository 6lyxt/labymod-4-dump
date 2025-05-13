// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;

public class ManifestEntry extends AbstractEntry<ManifestEntry>
{
    public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";
    
    public ManifestEntry(final long time, final byte[] data) {
        super("META-INF/MANIFEST.MF", time, data);
    }
    
    @Override
    public ManifestEntry process(final EntryTransformer<ManifestEntry> transformer) {
        return transformer.process(this);
    }
}
