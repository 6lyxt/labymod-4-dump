// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.version.parser;

public class VersionManifestException extends RuntimeException
{
    public VersionManifestException() {
    }
    
    public VersionManifestException(final String message) {
        super(message);
    }
    
    public VersionManifestException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public VersionManifestException(final Throwable cause) {
        super(cause);
    }
}
