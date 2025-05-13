// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.accessor;

import java.lang.reflect.Type;
import java.lang.reflect.Field;
import net.labymod.api.configuration.settings.accessor.impl.ConfigPropertySettingAccessor;
import java.lang.reflect.ParameterizedType;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.util.logging.Logging;

public class ConfigPropertySettingAccessorFactory implements SettingAccessorFactory
{
    private static final Logging LOGGER;
    
    @Override
    public SettingAccessor create(final SettingElement element, final SettingAccessorContext context, final Config config) {
        final Field field = context.field();
        field.setAccessible(true);
        final ConfigProperty property = Reflection.invokeGetterField(config, field);
        if (property == null) {
            ConfigPropertySettingAccessorFactory.LOGGER.warn("Could not create ConfigPropertySettingAccessor for \"{}\"", field.getName());
            return null;
        }
        property.withSettings(element);
        final Type genericType = field.getGenericType();
        if (genericType instanceof final ParameterizedType parameterizedType) {
            property.setGenericType(parameterizedType.getActualTypeArguments()[0]);
        }
        return new ConfigPropertySettingAccessor(element, property, config, field);
    }
    
    @Override
    public boolean isAssignableFrom(final Class<?> type) {
        return ConfigProperty.class.isAssignableFrom(type);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
