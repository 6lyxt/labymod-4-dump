// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.accessor;

import java.lang.reflect.Field;
import net.labymod.api.configuration.settings.accessor.impl.ReflectionSettingAccessor;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.util.logging.Logging;

public class ReflectionSettingAccessorFactory implements SettingAccessorFactory
{
    private static final Logging LOGGER;
    
    @Override
    public SettingAccessor create(final SettingElement element, final SettingAccessorContext context, final Config config) {
        final Field field = context.field();
        if (!Config.class.isAssignableFrom(field.getType())) {
            ReflectionSettingAccessorFactory.LOGGER.warn("""
                                                         *** Code of Conduct ***
                                                         It was detected that the property "{}" in the configuration "{}" is not a ConfigProperty.
                                                         Please update the property to a ConfigProperty!""", field.getName(), config.getClass().getName());
        }
        return new ReflectionSettingAccessor(element, field, config);
    }
    
    @Override
    public boolean isAssignableFrom(final Class<?> type) {
        return true;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
