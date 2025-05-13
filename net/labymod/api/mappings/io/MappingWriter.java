// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings.io;

import net.labymod.api.mappings.MethodMapping;
import net.labymod.api.mappings.FieldMapping;
import java.io.IOException;
import net.labymod.api.mappings.ClassMapping;

public interface MappingWriter extends AutoCloseable
{
    void writeClass(final ClassMapping p0, final boolean p1) throws IOException;
    
    void writeField(final FieldMapping p0, final boolean p1) throws IOException;
    
    void writeMethod(final MethodMapping p0, final boolean p1) throws IOException;
}
