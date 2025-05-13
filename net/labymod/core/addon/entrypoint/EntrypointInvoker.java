// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.entrypoint;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.models.addon.info.AddonEntrypoint;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class EntrypointInvoker
{
    private static final Logging LOGGER;
    
    public static void invoke(final AddonEntrypoint entrypoint, final ClassLoader classLoader) {
        final String entrypointName = entrypoint.name();
        final boolean invoked = invoke(classLoader, entrypointName);
        if (!invoked) {
            EntrypointInvoker.LOGGER.error("The entry point {} could not be invoked because no empty constructor was found!", entrypointName);
        }
    }
    
    private static boolean invoke(final ClassLoader classLoader, final String qualifiedEntrypoint) {
        try {
            final Class<?> entrypointClass = classLoader.loadClass(qualifiedEntrypoint);
            final Class<?> ift = classLoader.loadClass("net.labymod.api.addon.entrypoint.Entrypoint");
            if (!ift.isAssignableFrom(entrypointClass)) {
                throw new AddonLoadException();
            }
            final Constructor<?> constructor = Reflection.searchEmptyConstructor(entrypointClass);
            if (constructor == null) {
                return false;
            }
            final Object entrypointObject = constructor.newInstance(new Object[0]);
            final Method initializeMethod = entrypointClass.getDeclaredMethod("initialize", Version.class);
            initializeMethod.invoke(entrypointObject, DefaultLabyModLoader.getInstance().version());
            return true;
        }
        catch (final ReflectiveOperationException exception) {
            throw new AddonLoadException("Failed to instantiate addon entry point " + qualifiedEntrypoint, (Throwable)exception);
        }
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger(EntrypointInvoker.class);
    }
}
