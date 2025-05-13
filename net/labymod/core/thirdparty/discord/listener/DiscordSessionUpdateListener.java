// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.discord.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.session.Session;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import javax.inject.Singleton;

@Singleton
public class DiscordSessionUpdateListener
{
    private final DefaultDiscordApp discordRichPresence;
    
    @Inject
    public DiscordSessionUpdateListener() {
        this.discordRichPresence = (DefaultDiscordApp)Laby.references().discordApp();
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        final Session session = event.newSession();
        if (!session.isPremium()) {
            return;
        }
        this.discordRichPresence.updateUserAsset(session.getUsername(), session.getUniqueId());
    }
}
