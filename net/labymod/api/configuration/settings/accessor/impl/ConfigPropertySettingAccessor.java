// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.accessor.impl;

import org.jetbrains.annotations.Nullable;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;

public class ConfigPropertySettingAccessor implements SettingAccessor
{
    private final SettingElement setting;
    private final ConfigProperty configProperty;
    private final Config config;
    private final Field field;
    
    public ConfigPropertySettingAccessor(final SettingElement setting, final ConfigProperty configProperty, final Config config, final Field field) {
        this.setting = setting;
        this.configProperty = configProperty;
        this.config = config;
        this.field = field;
    }
    
    @Override
    public Class<?> getType() {
        return this.configProperty.getType();
    }
    
    @Nullable
    @Override
    public Type getGenericType() {
        return this.configProperty.getGenericType();
    }
    
    @Override
    public Field getField() {
        return this.field;
    }
    
    @Override
    public Config config() {
        return this.config;
    }
    
    @Override
    public <T> void set(final T value) {
        this.configProperty.set(value);
    }
    
    @Override
    public <T> T get() {
        return this.configProperty.get();
    }
    
    @Override
    public <T> ConfigProperty<T> property() {
        return this.configProperty;
    }
    
    @Override
    public SettingElement setting() {
        return this.setting;
    }
}
