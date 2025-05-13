// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings;

import java.io.IOException;
import java.util.Iterator;
import net.labymod.api.mappings.io.MappingWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClassMapping extends AbstractMapping
{
    private final Map<String, FieldMapping> fields;
    private final Map<String, MethodMapping> methods;
    
    public ClassMapping(final String original, final String mapped) {
        super(original, mapped);
        this.fields = new HashMap<String, FieldMapping>();
        this.methods = new HashMap<String, MethodMapping>();
    }
    
    public void addField(final FieldMapping fieldMapping) {
        fieldMapping.setParent(this);
        this.fields.put(fieldMapping.getOriginal(), fieldMapping);
    }
    
    public void addMethod(final MethodMapping methodMapping) {
        methodMapping.setParent(this);
        this.methods.put(methodMapping.getOriginal() + methodMapping.getOriginalDescriptor(), methodMapping);
    }
    
    public Collection<FieldMapping> getFields() {
        return this.fields.values();
    }
    
    public Collection<MethodMapping> getMethods() {
        return this.methods.values();
    }
    
    public String remapField(final String name) {
        final FieldMapping fieldMapping = this.fields.get(name);
        return (fieldMapping == null) ? name : fieldMapping.getMapped();
    }
    
    public MethodMapping remapMethod(final String name, final String desc) {
        return this.methods.get(name + desc);
    }
    
    @Override
    public void write(final MappingWriter writer, final boolean reversed) throws IOException {
        writer.writeClass(this, reversed);
        for (final FieldMapping field : this.getFields()) {
            writer.writeField(field, reversed);
        }
        for (final MethodMapping method : this.getMethods()) {
            writer.writeMethod(method, reversed);
        }
    }
}
