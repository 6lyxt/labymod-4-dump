// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerController;
import javax.inject.Singleton;

@Singleton
public class ServerScreenshotHandler
{
    private final ServerController serverController;
    
    @Inject
    public ServerScreenshotHandler() {
        this.serverController = Laby.references().serverController();
    }
    
    @Subscribe(Byte.MIN_VALUE)
    public void storeLastScreenshot(final WriteScreenshotEvent event) {
        if (event.isCancelled() || event.getPhase() != Phase.POST) {
            return;
        }
        final StorageServerData serverData = this.serverController.getCurrentStorageServerData();
        if (serverData == null) {
            return;
        }
        serverData.metadata().put("LastScreenshotFile", event.getDestination().getAbsolutePath());
        this.serverController.serverList().saveAsync();
    }
}
