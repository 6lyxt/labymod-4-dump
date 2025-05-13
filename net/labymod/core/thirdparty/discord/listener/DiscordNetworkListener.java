// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.discord.listener;

import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import javax.inject.Singleton;

@Singleton
public final class DiscordNetworkListener
{
    private final DefaultDiscordApp discordApp;
    
    @Inject
    public DiscordNetworkListener() {
        this.discordApp = (DefaultDiscordApp)Laby.references().discordApp();
    }
    
    @Subscribe
    public void onNetworkLogin(final NetworkLoginEvent event) {
        this.discordApp.updateServerInfo(event.serverData(), true);
    }
    
    @Subscribe
    public void onNetworkDisconnect(final NetworkDisconnectEvent event) {
        this.discordApp.displayDefaultActivity();
    }
}
