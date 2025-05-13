// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session.exceptions;

public class InvalidCredentialsException extends AuthenticationException
{
    public InvalidCredentialsException() {
    }
    
    public InvalidCredentialsException(final String message) {
        super(message);
    }
    
    public InvalidCredentialsException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public InvalidCredentialsException(final Throwable cause) {
        super(cause);
    }
}
