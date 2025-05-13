// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.service;

public class ServiceStatus
{
    private final Status status;
    
    ServiceStatus(final Status status) {
        this.status = status;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public static ServiceStatus of(final Status status) {
        return new ServiceStatus(status);
    }
    
    public enum Status
    {
        NOT_CONNECTED, 
        UNAUTHORIZED, 
        NOT_LINKED, 
        NEEDS_RELINK, 
        OK, 
        LOADING, 
        ERROR;
    }
}
