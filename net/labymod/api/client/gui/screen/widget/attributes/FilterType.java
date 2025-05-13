// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

public enum FilterType
{
    BLUR("blur", (Class<?>[])new Class[] { Float.TYPE });
    
    private final String name;
    private final Class<?>[] types;
    
    private FilterType(final String name, final Class<?>[] types) {
        this.name = name;
        this.types = types;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<?>[] getTypes() {
        return this.types;
    }
}
