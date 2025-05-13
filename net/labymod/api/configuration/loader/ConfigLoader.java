// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader;

import java.io.IOException;
import java.util.Locale;
import net.labymod.api.configuration.loader.annotation.ConfigPath;
import java.util.Iterator;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.configuration.exception.ConfigurationLoadException;

public interface ConfigLoader
{
     <T extends ConfigAccessor> T load(final Class<T> p0) throws ConfigurationLoadException;
    
    void save(final ConfigAccessor p0) throws ConfigurationSaveException;
    
    Object serialize(final ConfigAccessor p0) throws Exception;
    
    default String getName(final Class<? extends ConfigAccessor> clazz) {
        ConfigName annotation = null;
        for (final Class<?> treeClass : Reflection.getClassTree(clazz)) {
            final ConfigName treeClassAnnotation = treeClass.getAnnotation(ConfigName.class);
            if (treeClassAnnotation != null) {
                annotation = treeClassAnnotation;
                break;
            }
        }
        return (annotation == null) ? "settings" : annotation.value();
    }
    
    default String getRelativePath(final Class<? extends ConfigAccessor> clazz) {
        ConfigPath annotation = null;
        for (final Class<?> treeClass : Reflection.getClassTree(clazz)) {
            final ConfigPath treeClassAnnotation = treeClass.getAnnotation(ConfigPath.class);
            if (treeClassAnnotation != null) {
                annotation = treeClassAnnotation;
                break;
            }
        }
        return String.format(Locale.ROOT, "%s/%s", (annotation == null) ? "." : annotation.value(), getName(clazz));
    }
    
    void setVariable(final String p0, final Object p1);
    
    String getFileExtension();
    
    void invalidate(final Class<? extends ConfigAccessor> p0) throws IOException;
}
