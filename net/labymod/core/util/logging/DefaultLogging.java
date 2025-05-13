// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.logging;

import net.labymod.api.Constants;
import net.labymod.api.util.CharSequences;
import net.labymod.core.addon.AddonClassLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.labymod.api.util.logging.Logging;

final class DefaultLogging implements Logging
{
    private static final boolean DEBUG_LOGGING;
    private final Logger logger;
    private final String name;
    
    public DefaultLogging(final String name) {
        this.name = name;
        this.logger = LogManager.getLogger(name);
        if (DefaultLogging.DEBUG_LOGGING) {
            final Logger logger = this.logger;
            if (logger instanceof final org.apache.logging.log4j.core.Logger loggerImpl) {
                loggerImpl.setLevel(Level.DEBUG);
            }
            else {
                this.warn("Failed to set debug log level for logger: " + name, new Object[0]);
            }
        }
    }
    
    public DefaultLogging(final Class<?> cls) {
        this(buildLoggerName(cls));
    }
    
    private static String buildLoggerName(final Class<?> cls) {
        final String name = cls.getSimpleName();
        String namespace = "LabyMod";
        final ClassLoader classLoader = cls.getClassLoader();
        if (classLoader instanceof final AddonClassLoader addonClassLoader) {
            namespace = addonClassLoader.getAddonInfo().getNamespace();
        }
        return name + "/" + namespace;
    }
    
    @Override
    public void info(final CharSequence message, final Throwable throwable) {
        this.logger.info(CharSequences.toString(message), throwable);
    }
    
    @Override
    public void info(final CharSequence message, final Object... arguments) {
        this.logger.info(CharSequences.toString(message), arguments);
    }
    
    @Override
    public void warn(final CharSequence message, final Throwable throwable) {
        this.logger.warn(CharSequences.toString(message), throwable);
    }
    
    @Override
    public void warn(final CharSequence message, final Object... arguments) {
        this.logger.warn(CharSequences.toString(message), arguments);
    }
    
    @Override
    public void error(final CharSequence message, final Throwable throwable) {
        this.logger.error(CharSequences.toString(message), throwable);
    }
    
    @Override
    public void error(final CharSequence message, final Object... arguments) {
        this.logger.error(CharSequences.toString(message), arguments);
    }
    
    @Override
    public void debug(final CharSequence message, final Throwable throwable) {
        this.logger.debug(CharSequences.toString(message), throwable);
    }
    
    @Override
    public void debug(final CharSequence message, final Object... arguments) {
        this.logger.debug(CharSequences.toString(message), arguments);
    }
    
    static {
        DEBUG_LOGGING = (Constants.SystemProperties.getBoolean("net.labymod.debug-logger") || Constants.SystemProperties.getBoolean(Constants.SystemProperties.ALL));
    }
}
