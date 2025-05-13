// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug.filewatcher;

public enum NamedPathType implements PathType
{
    UNKNOWN(new String[0]), 
    TEXTURE(new String[] { ".png", ".jpg" }), 
    LSS(new String[] { ".lss" });
    
    public static final NamedPathType[] VALUES;
    private final String[] fileExtensions;
    
    private NamedPathType(final String[] fileExtensions) {
        this.fileExtensions = fileExtensions;
    }
    
    @Override
    public String[] getFileExtensions() {
        return this.fileExtensions;
    }
    
    static {
        VALUES = values();
    }
}
