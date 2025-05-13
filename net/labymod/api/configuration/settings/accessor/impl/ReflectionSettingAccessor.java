// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.accessor.impl;

import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.Nullable;
import java.lang.reflect.Type;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.Config;
import java.lang.reflect.Field;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;

public class ReflectionSettingAccessor implements SettingAccessor
{
    private final SettingElement setting;
    private final Field field;
    private final Config config;
    private final ConfigProperty<?> property;
    
    public ReflectionSettingAccessor(final SettingElement setting, final Field field, final Config config) {
        this.setting = setting;
        this.field = field;
        this.config = config;
        this.field.setAccessible(true);
        this.property = new ConfigProperty<Object>(this.get());
    }
    
    @Override
    public Class<?> getType() {
        return this.field.getType();
    }
    
    @Nullable
    @Override
    public Type getGenericType() {
        return this.field.getGenericType();
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
    public <T> void set(T value) {
        if (this.setting.isInitialized()) {
            final SettingUpdateEvent event = new SettingUpdateEvent(Phase.PRE, this.setting, value);
            Laby.labyAPI().eventBus().fire(event);
            value = event.getValue();
        }
        Reflection.invokeSetterField(this.config, this.field, value);
        final SettingUpdateEvent event = new SettingUpdateEvent(Phase.POST, this.setting, value);
        Laby.labyAPI().eventBus().fire(event);
    }
    
    @Override
    public <T> T get() {
        return Reflection.invokeGetterField(this.config, this.field);
    }
    
    @Override
    public <T> ConfigProperty<T> property() {
        this.property.set(this.get());
        return (ConfigProperty<T>)this.property;
    }
    
    @Override
    public SettingElement setting() {
        return this.setting;
    }
}
