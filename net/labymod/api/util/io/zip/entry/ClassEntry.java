// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip.entry;

import net.labymod.api.util.io.zip.EntryTransformer;

public class ClassEntry extends AbstractEntry<ClassEntry>
{
    private static final int CLASS_LENGTH;
    private final String className;
    
    public ClassEntry(final String name, final long time, final byte[] data) {
        super(name, time, data);
        this.className = name.substring(0, name.length() - ClassEntry.CLASS_LENGTH);
    }
    
    public String getClassName() {
        return this.className;
    }
    
    @Override
    public ClassEntry process(final EntryTransformer<ClassEntry> transformer) {
        return transformer.process(this);
    }
    
    static {
        CLASS_LENGTH = ".class".length();
    }
}
