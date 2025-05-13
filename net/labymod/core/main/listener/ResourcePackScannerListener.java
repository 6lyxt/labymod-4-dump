// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePackScanner;

public class ResourcePackScannerListener
{
    private final ResourcePackScanner resourcePackScanner;
    
    public ResourcePackScannerListener() {
        this.resourcePackScanner = Laby.references().resourcePackScanner();
    }
    
    @Subscribe
    public void onResourceReload(final ResourceReloadEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.resourcePackScanner.scan();
    }
}
