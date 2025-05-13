// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings;

import java.io.IOException;
import net.labymod.api.mappings.io.MappingWriter;

public class FieldMapping extends AbstractMapping implements ScopedMapping<ClassMapping>
{
    private final String originalDescriptor;
    private final String mappedDescriptor;
    private ClassMapping parent;
    
    public FieldMapping(final String original, final String mapped) {
        this(original, null, mapped, null);
    }
    
    public FieldMapping(final String original, final String originalDescriptor, final String mapped, final String mappedDescriptor) {
        super(original, mapped);
        this.originalDescriptor = originalDescriptor;
        this.mappedDescriptor = mappedDescriptor;
    }
    
    @Override
    public ClassMapping getParent() {
        return this.parent;
    }
    
    void setParent(final ClassMapping parent) {
        this.parent = parent;
    }
    
    public String getOriginalDescriptor() {
        return this.originalDescriptor;
    }
    
    public String getMappedDescriptor() {
        return this.mappedDescriptor;
    }
    
    @Override
    public void write(final MappingWriter writer, final boolean reversed) throws IOException {
        writer.writeField(this, reversed);
    }
}
