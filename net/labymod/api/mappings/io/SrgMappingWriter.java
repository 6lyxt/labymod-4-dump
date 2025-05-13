// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mappings.io;

import net.labymod.api.mappings.MethodMapping;
import net.labymod.api.mappings.FieldMapping;
import java.io.IOException;
import net.labymod.api.mappings.ClassMapping;
import java.io.Writer;

public class SrgMappingWriter implements MappingWriter
{
    private final Writer writer;
    
    public SrgMappingWriter(final Writer writer) {
        this.writer = writer;
    }
    
    @Override
    public void writeClass(final ClassMapping mapping, final boolean reversed) throws IOException {
        final String original = mapping.getOriginal();
        final String mapped = mapping.getMapped();
        this.writer.write("CL: " + String.valueOf(reversed ? mapping : original) + " " + (reversed ? original : mapped));
        this.writer.write(10);
        this.writer.flush();
    }
    
    @Override
    public void writeField(final FieldMapping mapping, final boolean reversed) throws IOException {
        final ClassMapping parent = mapping.getParent();
        if (reversed) {
            this.writer.write("FD: " + parent.getMapped() + "/" + mapping.getMapped() + " " + parent.getOriginal() + "/" + mapping.getOriginal());
        }
        else {
            this.writer.write("FD: " + parent.getOriginal() + "/" + mapping.getOriginal() + " " + parent.getMapped() + "/" + mapping.getMapped());
        }
        this.writer.write(10);
        this.writer.flush();
    }
    
    @Override
    public void writeMethod(final MethodMapping mapping, final boolean reversed) throws IOException {
        final ClassMapping parent = mapping.getParent();
        if (reversed) {
            this.writer.write("MD: " + parent.getMapped() + "/" + mapping.getMapped() + " " + mapping.getMappedDescriptor() + " " + parent.getOriginal() + "/" + mapping.getOriginal() + " " + mapping.getOriginalDescriptor());
        }
        else {
            this.writer.write("MD: " + parent.getOriginal() + "/" + mapping.getOriginal() + " " + mapping.getOriginalDescriptor() + " " + parent.getMapped() + "/" + mapping.getMapped() + " " + mapping.getMappedDescriptor());
        }
        this.writer.write(10);
        this.writer.flush();
    }
    
    @Override
    public void close() throws Exception {
        this.writer.close();
    }
}
