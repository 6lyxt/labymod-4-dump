// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings.io;

import net.labymod.api.mappings.MappingFile;
import java.io.InputStream;

public interface MappingReader
{
    MappingFile read(final InputStream p0);
}
