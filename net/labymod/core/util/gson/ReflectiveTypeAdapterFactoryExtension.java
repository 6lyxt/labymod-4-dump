// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.gson;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.reflection.Reflection;
import java.lang.reflect.Field;

public final class ReflectiveTypeAdapterFactoryExtension
{
    public static void handleCustomField(final Field field, final Object instance, final Object value) {
        final Object obj = Reflection.invokeGetterField(instance, field);
        if (obj instanceof final ConfigProperty configProperty) {
            if (value instanceof final ConfigProperty configProperty2) {
                configProperty.set(configProperty2.get());
            }
            else {
                configProperty.set(value);
            }
            return;
        }
        Reflection.invokeSetterField(instance, field, value);
    }
}
