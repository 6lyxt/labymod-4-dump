// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;

public interface Entry<T extends Entry<T>>
{
    String getName();
    
    long getTime();
    
    byte[] getData();
    
    T process(final EntryTransformer<T> p0);
}
