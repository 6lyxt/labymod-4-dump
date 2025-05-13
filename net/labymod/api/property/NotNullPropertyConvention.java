// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.property;

public class NotNullPropertyConvention<T> implements PropertyConvention<T>
{
    private final T defaultValue;
    
    public NotNullPropertyConvention(final T defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    @Override
    public T convention(final T value) {
        return (value == null) ? this.defaultValue : value;
    }
}
