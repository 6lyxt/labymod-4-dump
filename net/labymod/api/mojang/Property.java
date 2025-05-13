// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mojang;

import java.util.Objects;

public class Property
{
    private final String name;
    private final String value;
    private final String signature;
    
    public Property(final String value, final String name) {
        this(value, name, null);
    }
    
    public Property(final String name, final String value, final String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public boolean hasSignature() {
        return this.signature != null;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Property property = (Property)o;
        return Objects.equals(this.name, property.name) && Objects.equals(this.value, property.value) && Objects.equals(this.signature, property.signature);
    }
    
    @Override
    public int hashCode() {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = 31 * result + ((this.value != null) ? this.value.hashCode() : 0);
        result = 31 * result + ((this.signature != null) ? this.signature.hashCode() : 0);
        return result;
    }
}
