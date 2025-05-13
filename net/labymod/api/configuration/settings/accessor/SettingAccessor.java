// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.accessor;

import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.Config;
import java.lang.reflect.Field;
import org.jetbrains.annotations.Nullable;
import java.lang.reflect.Type;

public interface SettingAccessor
{
    Class<?> getType();
    
    @Nullable
    Type getGenericType();
    
    Field getField();
    
    Config config();
    
     <T> void set(final T p0);
    
     <T> T get();
    
     <T> ConfigProperty<T> property();
    
    default boolean isSet() {
        return this.get() != null;
    }
    
    SettingElement setting();
}
