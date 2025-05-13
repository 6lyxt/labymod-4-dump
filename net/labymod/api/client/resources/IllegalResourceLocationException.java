// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

public class IllegalResourceLocationException extends RuntimeException
{
    public IllegalResourceLocationException(final String message) {
        super(escapeJavaString(message));
    }
    
    public IllegalResourceLocationException(final String message, final Throwable cause) {
        super(escapeJavaString(message), cause);
    }
    
    public IllegalResourceLocationException(final Throwable cause) {
        super(cause);
    }
    
    private static String escapeJavaString(final String value) {
        return "\"" + value;
    }
}
