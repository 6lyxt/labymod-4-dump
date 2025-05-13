// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.zip;

import java.io.IOException;

public class ZipException extends IOException
{
    public ZipException(final String message) {
        super(message);
    }
    
    public ZipException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
