// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session.exceptions;

public class AuthenticationException extends Exception
{
    public AuthenticationException() {
    }
    
    public AuthenticationException(final String message) {
        super(message);
    }
    
    public AuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public AuthenticationException(final Throwable cause) {
        super(cause);
    }
}
