// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;

public class ResourceEntry extends AbstractEntry<ResourceEntry>
{
    public ResourceEntry(final String name, final long time, final byte[] data) {
        super(name, time, data);
    }
    
    @Override
    public ResourceEntry process(final EntryTransformer<ResourceEntry> transformer) {
        return transformer.process(this);
    }
}
