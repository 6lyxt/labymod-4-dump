// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session.exceptions;

public class AuthenticationUnavailableException extends AuthenticationException
{
    public AuthenticationUnavailableException() {
    }
    
    public AuthenticationUnavailableException(final String message) {
        super(message);
    }
    
    public AuthenticationUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public AuthenticationUnavailableException(final Throwable cause) {
        super(cause);
    }
}
