// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.service;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ChainedService extends Service
{
    private final List<Service> services;
    
    public ChainedService() {
        this.services = new ArrayList<Service>();
    }
    
    protected <T extends Service> T registerService(final T service) {
        this.services.add(service);
        return service;
    }
    
    @MustBeInvokedByOverriders
    @Override
    public void onServiceCompleted() {
        for (final Service service : this.services) {
            service.prepareSynchronously();
        }
    }
    
    @MustBeInvokedByOverriders
    @Override
    public void onServiceUnload() {
        for (final Service service : this.services) {
            service.unload();
        }
    }
}
