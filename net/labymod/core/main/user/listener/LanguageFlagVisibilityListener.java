// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.listener;

import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.user.GameUser;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.core.main.user.DefaultGameUserService;

public final class LanguageFlagVisibilityListener
{
    private final DefaultGameUserService gameUserService;
    
    public LanguageFlagVisibilityListener(final DefaultGameUserService gameUserService) {
        this.gameUserService = gameUserService;
    }
    
    @Subscribe
    public void onServerJoin(final ServerJoinEvent event) {
        for (final GameUser gameUser : this.gameUserService.getGameUsers().values()) {
            if (!(gameUser instanceof DefaultGameUser)) {
                continue;
            }
            final DefaultGameUser defaultGameUser = (DefaultGameUser)gameUser;
            defaultGameUser.setCountryCode(null);
        }
    }
}
