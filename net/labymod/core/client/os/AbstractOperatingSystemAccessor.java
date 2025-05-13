// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os;

import java.io.IOException;
import net.labymod.api.Laby;
import java.io.InputStream;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.os.OperatingSystemAccessor;

public abstract class AbstractOperatingSystemAccessor implements OperatingSystemAccessor
{
    private static final Logging LOGGER;
    
    public abstract CredentialsAccessor credentialsAccessor() throws UnsupportedOperationException;
    
    @Override
    public void setWindowIcon(final long handle, final InputStream stream) {
        try {
            Laby.gfx().setGLFWIcon(handle, stream);
        }
        catch (final IOException exception) {
            AbstractOperatingSystemAccessor.LOGGER.error("Failed to set window icon", exception);
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
