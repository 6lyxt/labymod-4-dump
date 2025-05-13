// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi;

import net.labymod.api.util.logging.Logging;
import net.labymod.serverapi.api.logger.ProtocolPlatformLogger;

public final class LabyModProtocolPlatformLogger implements net.labymod.serverapi.api.logger.ProtocolPlatformLogger, ProtocolPlatformLogger
{
    private static final Logging LOGGING;
    
    public void info(final String message, final Object... objects) {
        LabyModProtocolPlatformLogger.LOGGING.info(message, objects);
    }
    
    public void info(final String message, final Throwable throwable) {
        LabyModProtocolPlatformLogger.LOGGING.info(message, throwable);
    }
    
    public void warn(final String message, final Object... objects) {
        LabyModProtocolPlatformLogger.LOGGING.warn(message, objects);
    }
    
    public void warn(final String message, final Throwable throwable) {
        LabyModProtocolPlatformLogger.LOGGING.warn(message, throwable);
    }
    
    public void error(final String message, final Object... objects) {
        LabyModProtocolPlatformLogger.LOGGING.error(message, objects);
    }
    
    public void error(final String message, final Throwable throwable) {
        LabyModProtocolPlatformLogger.LOGGING.error(message, throwable);
    }
    
    static {
        LOGGING = Logging.getLogger();
    }
}
