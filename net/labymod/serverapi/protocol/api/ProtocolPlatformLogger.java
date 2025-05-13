// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.api;

@Deprecated(forRemoval = true, since = "4.2.24")
public interface ProtocolPlatformLogger
{
    void info(final String p0, final Object... p1);
    
    void info(final String p0, final Throwable p1);
    
    void warn(final String p0, final Object... p1);
    
    void warn(final String p0, final Throwable p1);
    
    void error(final String p0, final Object... p1);
    
    void error(final String p0, final Throwable p1);
}
