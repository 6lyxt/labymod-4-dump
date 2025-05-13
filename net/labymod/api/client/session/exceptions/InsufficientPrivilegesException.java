// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session.exceptions;

public class InsufficientPrivilegesException extends AuthenticationException
{
    public InsufficientPrivilegesException() {
    }
    
    public InsufficientPrivilegesException(final String message) {
        super(message);
    }
    
    public InsufficientPrivilegesException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public InsufficientPrivilegesException(final Throwable cause) {
        super(cause);
    }
}
