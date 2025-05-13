// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty;

import net.labymod.api.property.Property;
import net.labymod.core.thirdparty.discord.listener.DiscordSessionUpdateListener;
import net.labymod.core.thirdparty.discord.listener.DiscordSingleplayerListener;
import net.labymod.core.thirdparty.discord.listener.DiscordNetworkListener;
import net.labymod.core.thirdparty.listener.ThirdPartyTickListener;
import javax.inject.Inject;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import net.labymod.api.Laby;
import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.api.event.EventBus;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.thirdparty.ThirdPartyService;

@Singleton
@Implements(ThirdPartyService.class)
public class DefaultThirdPartyService implements ThirdPartyService
{
    private final LabyAPI labyAPI;
    private final EventBus eventBus;
    private final DiscordApp discordRichPresence;
    
    @Inject
    public DefaultThirdPartyService(final LabyAPI labyAPI, final EventBus eventBus, final DiscordApp discordRichPresence) {
        this.labyAPI = labyAPI;
        this.eventBus = eventBus;
        this.discordRichPresence = discordRichPresence;
        final DiscordConfig config = this.labyAPI.config().other().discord();
        config.enabled().addChangeListener((type, oldValue, newValue) -> {
            final ThirdPartyService thirdPartyService = Laby.labyAPI().thirdPartyService();
            if (thirdPartyService == null) {
                return;
            }
            else {
                final DiscordApp discordApp = thirdPartyService.discord();
                if (!(discordApp instanceof DefaultDiscordApp)) {
                    return;
                }
                else {
                    final DefaultDiscordApp defaultDiscordApp = (DefaultDiscordApp)discordApp;
                    if (newValue) {
                        defaultDiscordApp.initialize();
                        defaultDiscordApp.updateServerInfo(true);
                    }
                    else {
                        defaultDiscordApp.dispose();
                    }
                    return;
                }
            }
        });
        config.showServerAddress().addChangeListener((type, oldValue, newValue) -> {
            final ThirdPartyService thirdPartyService2 = Laby.labyAPI().thirdPartyService();
            if (thirdPartyService2 != null) {
                final DiscordApp discordApp2 = thirdPartyService2.discord();
                if (!(!(discordApp2 instanceof DefaultDiscordApp))) {
                    ((DefaultDiscordApp)discordApp2).updateServerInfo(false);
                }
            }
        });
    }
    
    @Override
    public void initialize() {
        this.eventBus.registerListener(new ThirdPartyTickListener());
        this.initializeDiscordApp();
    }
    
    @Override
    public DiscordApp discord() {
        return this.discordRichPresence;
    }
    
    private void initializeDiscordApp() {
        this.eventBus.registerListener(new DiscordNetworkListener());
        this.eventBus.registerListener(new DiscordSingleplayerListener());
        this.eventBus.registerListener(new DiscordSessionUpdateListener());
        if (this.labyAPI.config().other().discord().enabled().get()) {
            ((DefaultDiscordApp)this.discordRichPresence).initialize();
        }
    }
}
