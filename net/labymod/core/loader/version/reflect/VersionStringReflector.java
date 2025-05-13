// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.version.reflect;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.util.IsolatedClassLoader;

public final class VersionStringReflector
{
    private final String className;
    private final String fieldName;
    
    public VersionStringReflector(final String className, final String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }
    
    public void invoke(final IsolatedClassLoader loader, final Consumer<String> versionConsumer) {
        Objects.requireNonNull(versionConsumer);
        try {
            final Class<?> cls = loader.loadClass(this.className);
            for (final Field field : cls.getDeclaredFields()) {
                if (field.getName().equals(this.fieldName)) {
                    versionConsumer.accept((String)field.get(null));
                    break;
                }
            }
        }
        catch (final ReflectiveOperationException exception) {
            throw new RuntimeException(String.valueOf(this) + " could not be called", (Throwable)exception);
        }
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public String getFieldName() {
        return this.fieldName;
    }
    
    @Override
    public String toString() {
        return this.className + "." + this.fieldName;
    }
}
