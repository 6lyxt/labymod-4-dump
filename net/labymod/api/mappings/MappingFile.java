// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings;

import java.io.IOException;
import net.labymod.api.mappings.io.MappingWriter;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MappingFile
{
    private final Map<String, ClassMapping> classes;
    
    public MappingFile() {
        this(new HashMap<String, ClassMapping>());
    }
    
    public MappingFile(final Map<String, ClassMapping> classes) {
        this.classes = classes;
    }
    
    public ClassMapping getOrCreate(final String original, final String mapped) {
        ClassMapping cached = this.classes.get(original);
        if (cached != null) {
            return cached;
        }
        cached = new ClassMapping(original, mapped);
        this.addClass(cached);
        return cached;
    }
    
    public void addClass(final ClassMapping classMapping) {
        this.classes.put(classMapping.getOriginal(), classMapping);
    }
    
    public ClassMapping getClass(final String name) {
        return this.classes.get(name);
    }
    
    public Collection<ClassMapping> getClasses() {
        return this.classes.values();
    }
    
    public MappingFile reverse() {
        final MappingFile mappingFile = new MappingFile();
        for (final ClassMapping cls : this.getClasses()) {
            final ClassMapping newClass = mappingFile.getOrCreate(cls.getMapped(), cls.getOriginal());
            for (final FieldMapping field : cls.getFields()) {
                newClass.addField(new FieldMapping(field.getMapped(), field.getMappedDescriptor(), field.getOriginal(), field.getOriginalDescriptor()));
            }
            for (final MethodMapping method : cls.getMethods()) {
                newClass.addMethod(new MethodMapping(method.getMapped(), method.getMappedDescriptor(), method.getOriginal(), method.getOriginalDescriptor()));
            }
        }
        return mappingFile;
    }
    
    public String remapClass(final String cls) {
        final ClassMapping classMapping = this.classes.get(cls);
        return (classMapping == null) ? cls : classMapping.getMapped();
    }
    
    public void write(final MappingWriter writer, final boolean reversed) throws IOException {
        for (final ClassMapping cls : this.getClasses()) {
            cls.write(writer, reversed);
        }
    }
}
