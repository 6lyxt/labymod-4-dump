// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.autotext;

import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.event.client.input.KeyEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.LabyAPI;
import javax.inject.Singleton;

@Singleton
public class AutoTextHandler
{
    private final LabyAPI labyAPI;
    private final ServerController serverController;
    
    @Inject
    public AutoTextHandler() {
        this.labyAPI = Laby.labyAPI();
        this.serverController = Laby.references().serverController();
    }
    
    @Subscribe
    public void handle(final KeyEvent event) {
        if (!this.labyAPI.permissionRegistry().isPermissionEnabled("chat_autotext")) {
            return;
        }
        if (this.labyAPI.minecraft().minecraftWindow().getCurrentVersionedScreen() != null) {
            return;
        }
        final AutoTextConfigAccessor autoTextConfig = Laby.references().chatProvider().autoTextConfigAccessor();
        if (event.state() != KeyEvent.State.PRESS || autoTextConfig.getEntries() == null || autoTextConfig.getEntries().isEmpty()) {
            return;
        }
        final Key key = event.key();
        for (final AutoTextEntry entry : autoTextConfig.getEntries()) {
            if (!entry.displayInInteractionMenu().get() && entry.requiresKey(key)) {
                if (!entry.isEveryKeyPressed()) {
                    continue;
                }
                if (!entry.isActiveOnCurrentServer()) {
                    continue;
                }
                final String autoTextMessage = entry.message().get();
                if (!entry.sendImmediately().get()) {
                    this.labyAPI.minecraft().openChat(autoTextMessage);
                    if (!key.isAction()) {
                        event.setCancelled(true);
                    }
                    return;
                }
                this.labyAPI.minecraft().chatExecutor().chat(autoTextMessage, false);
            }
        }
    }
}
