// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.property;

import java.util.function.Predicate;
import net.labymod.api.property.Property;
import net.labymod.api.util.function.ChangeListener;

public class ConvertListener<T> implements ChangeListener<Property<T>, T>
{
    private final ConfigProperty<?> newProperty;
    private final Predicate<T> predicate;
    
    private ConvertListener(final ConfigProperty<?> newProperty, final Predicate<T> predicate) {
        this.newProperty = newProperty;
        this.predicate = predicate;
    }
    
    public static <T> ConvertListener<T> of(final ConfigProperty<?> newProperty, final Predicate<T> predicate) {
        return new ConvertListener<T>(newProperty, predicate);
    }
    
    @Override
    public void changed(final Property<T> property, final T oldValue, final T newValue) {
        if (property.isDefaultValue(newValue) || !this.newProperty.isDefaultValue()) {
            return;
        }
        if (this.predicate.test(newValue)) {
            property.reset();
        }
    }
}
