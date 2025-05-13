// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import net.labymod.api.configuration.settings.annotation.SettingDevelopment;
import com.google.gson.FieldAttributes;
import net.labymod.api.LabyAPI;
import com.google.gson.ExclusionStrategy;

public class SettingDevelopmentExclusionStrategy implements ExclusionStrategy
{
    private final LabyAPI labyAPI;
    
    public SettingDevelopmentExclusionStrategy(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    public boolean shouldSkipField(final FieldAttributes f) {
        final SettingDevelopment annotation = (SettingDevelopment)f.getAnnotation((Class)SettingDevelopment.class);
        if (annotation == null) {
            return false;
        }
        final String configNamespace = this.labyAPI.getNamespace(f.getClass());
        return !this.labyAPI.labyModLoader().isDevelopmentEnvironment(configNamespace);
    }
    
    public boolean shouldSkipClass(final Class<?> clazz) {
        return false;
    }
}
