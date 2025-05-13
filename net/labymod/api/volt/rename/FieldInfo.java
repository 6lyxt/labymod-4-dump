// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.rename;

public class FieldInfo
{
    private final String name;
    private final String descriptor;
    
    public FieldInfo(final String name, final String descriptor) {
        this.name = name;
        this.descriptor = descriptor;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescriptor() {
        return this.descriptor;
    }
}
