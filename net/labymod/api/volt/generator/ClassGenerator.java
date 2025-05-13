// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.generator;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.volt.classloader.DefiningClassLoader;

public abstract class ClassGenerator
{
    protected final DefiningClassLoader definingClassLoader;
    
    public ClassGenerator(final Class<?> cls) {
        this(cls.getClassLoader());
    }
    
    public ClassGenerator(final ClassLoader parent) {
        this(new DefiningClassLoader(parent));
    }
    
    private ClassGenerator(final DefiningClassLoader definingClassLoader) {
        this.definingClassLoader = definingClassLoader;
    }
    
    @MustBeInvokedByOverriders
    public Class<?> generateClass(@NotNull String name, @Nullable final Context context) {
        Objects.requireNonNull(name, "name must not be null");
        name = name.replace('/', '.');
        final byte[] generatedClassData = this.generateClass(context);
        Objects.requireNonNull(generatedClassData, "generatedClassData must not be null");
        return this.definingClassLoader.defineClass(name, generatedClassData);
    }
    
    public abstract byte[] generateClass(@Nullable final Context p0);
    
    public interface Context
    {
    }
}
