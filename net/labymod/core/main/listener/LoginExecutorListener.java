// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.world.WorldEnterEvent;
import java.util.concurrent.TimeUnit;
import java.util.Objects;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.network.server.ServerController;

public class LoginExecutorListener
{
    private final ServerController serverController;
    private final Minecraft minecraft;
    private final Task delayedTask;
    private boolean packConfirmOpen;
    
    public LoginExecutorListener(final LabyAPI labyAPI) {
        this.minecraft = labyAPI.minecraft();
        this.serverController = labyAPI.serverController();
        final ServerController serverController = this.serverController;
        Objects.requireNonNull(serverController);
        this.delayedTask = Task.builder(serverController::executeLoginExecutor).delay(2500L, TimeUnit.MILLISECONDS).build();
    }
    
    @Subscribe
    public void onWorldEnter(final WorldEnterEvent event) {
        if (this.isConfirmScreenOpen()) {
            this.packConfirmOpen = true;
            return;
        }
        this.delayedTask.execute();
    }
    
    @Subscribe
    public void onResourceReload(final ResourceReloadEvent event) {
        if (event.phase() != Phase.POST || event.type() != ResourceReloadEvent.Type.RELOAD) {
            return;
        }
        if (this.packConfirmOpen) {
            this.delayedTask.execute();
            this.packConfirmOpen = false;
        }
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.packConfirmOpen = false;
    }
    
    private boolean isConfirmScreenOpen() {
        final Window window = this.minecraft.minecraftWindow();
        return window.isScreenDisplayed(NamedScreen.CONFIRM) || window.isScreenDisplayed(NamedScreen.PACK_CONFIRM);
    }
}
