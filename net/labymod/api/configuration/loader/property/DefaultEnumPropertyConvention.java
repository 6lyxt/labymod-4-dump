// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.property;

import net.labymod.api.property.PropertyConvention;

public class DefaultEnumPropertyConvention<E extends Enum<E>> implements PropertyConvention<E>
{
    private final E defaultEnum;
    
    public DefaultEnumPropertyConvention(final E defaultEnum) {
        this.defaultEnum = defaultEnum;
    }
    
    @Override
    public E convention(final E value) {
        return (value == null) ? this.defaultEnum : value;
    }
}
