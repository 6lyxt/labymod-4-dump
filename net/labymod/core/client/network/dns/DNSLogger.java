// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.dns;

import net.labymod.api.util.logging.Logging;
import org.xbill.DNS.logger.Logger;

public class DNSLogger implements Logger
{
    private final Logging logging;
    
    public DNSLogger(final String name) {
        this.logging = Logging.create(name);
    }
    
    public void info(final String s, final Object... objects) {
        this.logging.info(s, objects);
    }
    
    public void warn(final String s, final Object... objects) {
        this.logging.warn(s, objects);
    }
    
    public void error(final String s, final Object... objects) {
        this.logging.error(s, objects);
    }
    
    public void debug(final String s, final Object... objects) {
        this.logging.debug(s, objects);
    }
    
    public void trace(final String s, final Object... objects) {
    }
}
