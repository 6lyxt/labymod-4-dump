// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings;

import java.io.IOException;
import net.labymod.api.mappings.io.MappingWriter;

public interface Mapping
{
    String getOriginal();
    
    String getMapped();
    
    void write(final MappingWriter p0, final boolean p1) throws IOException;
}
