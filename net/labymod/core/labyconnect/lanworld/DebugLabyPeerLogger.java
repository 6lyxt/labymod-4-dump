// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.lanworld;

import java.util.Locale;
import net.labymod.api.util.logging.Logging;
import net.labymod.labypeer.logger.LabyPeerLogger;

public class DebugLabyPeerLogger implements LabyPeerLogger
{
    private static final Logging LOGGER;
    
    public void info(final String s, final Object... args) {
        DebugLabyPeerLogger.LOGGER.info(String.format(Locale.ROOT, s, args), new Object[0]);
    }
    
    public void error(final String s, final Throwable throwable, final Object... args) {
        DebugLabyPeerLogger.LOGGER.error(String.format(Locale.ROOT, s, args), throwable);
    }
    
    static {
        LOGGER = Logging.create(DebugLabyPeerLogger.class);
    }
}
