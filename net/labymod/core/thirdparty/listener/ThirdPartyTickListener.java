// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.listener;

import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.thirdparty.ThirdPartyService;
import net.labymod.api.LabyAPI;
import javax.inject.Singleton;

@Singleton
public final class ThirdPartyTickListener
{
    private final LabyAPI labyAPI;
    private final ThirdPartyService thirdPartyService;
    
    @Inject
    public ThirdPartyTickListener() {
        this.labyAPI = Laby.labyAPI();
        this.thirdPartyService = Laby.references().thirdPartyService();
    }
    
    @Subscribe
    public void onPostTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final DiscordApp discordApp = this.thirdPartyService.discord();
        if (discordApp.isRunning()) {
            if (!(discordApp instanceof DefaultDiscordApp)) {
                return;
            }
            final DefaultDiscordApp defaultDiscordApp = (DefaultDiscordApp)discordApp;
            this.handleDiscord(defaultDiscordApp);
            defaultDiscordApp.tick();
        }
    }
    
    @Subscribe
    public void gameAfterPostStartup(final GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.POST_STARTUP) {
            return;
        }
        final DiscordApp discordApp = this.thirdPartyService.discord();
        if (discordApp instanceof final DefaultDiscordApp defaultDiscordApp) {
            final SessionAccessor sessionAccessor = Laby.labyAPI().minecraft().sessionAccessor();
            final Session session = sessionAccessor.getSession();
            if (session != null && session.isPremium()) {
                defaultDiscordApp.updateUserAsset(session.getUsername(), session.getUniqueId());
            }
        }
    }
    
    private void handleDiscord(final DefaultDiscordApp discordApp) {
        if (this.labyAPI.serverController().getCurrentServerData() == null) {
            return;
        }
        final boolean connected = this.labyAPI.minecraft().isIngame();
        final DiscordActivity displayedActivity = discordApp.getDisplayedActivity();
        if (!connected || displayedActivity == null || displayedActivity.isCustom() || displayedActivity.getState() != null) {
            return;
        }
        final DiscordActivity activity = DiscordActivity.builder(this, displayedActivity).state("Ingame").build();
        if (displayedActivity.equals(activity)) {
            return;
        }
        discordApp.displayInternal(activity);
    }
}
