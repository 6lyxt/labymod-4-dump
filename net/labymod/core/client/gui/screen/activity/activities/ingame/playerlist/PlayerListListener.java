// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.event.client.gui.screen.playerlist.ServerBannerEvent;
import net.labymod.api.event.client.gui.screen.playerlist.PlayerListUserUpdateEvent;
import net.labymod.api.event.client.gui.screen.playerlist.PlayerListUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import java.util.Iterator;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryRemoveEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryAddEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;

public class PlayerListListener
{
    private final PlayerListOverlay playerList;
    
    public PlayerListListener(final PlayerListOverlay playerList) {
        this.playerList = playerList;
    }
    
    @Subscribe
    public void onTeamUpdate(final ScoreboardTeamUpdateEvent event) {
        this.playerList.forEachUserFromTeam(event.team(), CachedComponent::invalidate);
    }
    
    @Subscribe
    public void onTeamEntryAdd(final ScoreboardTeamEntryAddEvent event) {
        final PlayerListUser user = this.playerList.getUser(event.getEntry());
        if (user != null && user.updateTeam(event.team())) {
            this.playerList.needSorting();
        }
    }
    
    @Subscribe
    public void onTeamEntryRemove(final ScoreboardTeamEntryRemoveEvent event) {
        final PlayerListUser user = this.playerList.getUser(event.getEntry());
        if (user != null && user.updateTeam(null)) {
            this.playerList.needSorting();
        }
    }
    
    @Subscribe
    public void onPlayerInfoAdd(final PlayerInfoAddEvent event) {
        final NetworkPlayerInfo networkPlayerInfo = event.playerInfo();
        if (!networkPlayerInfo.isListed()) {
            return;
        }
        this.playerList.addUser(event.playerInfo());
    }
    
    @Subscribe
    public void onPlayerInfoUpdate(final PlayerInfoUpdateEvent event) {
        final PlayerInfoUpdateEvent.UpdateType type = event.type();
        switch (type) {
            case UPDATE_LISTED: {
                final NetworkPlayerInfo networkPlayerInfo = event.playerInfo();
                if (networkPlayerInfo.isListed()) {
                    this.playerList.addUser(networkPlayerInfo);
                }
                else {
                    this.playerList.removeUser(networkPlayerInfo);
                }
                break;
            }
            case GAME_MODE: {
                final PlayerListUser user = this.playerList.getUser(event.playerInfo());
                if (user != null && user.isSpectator() != (event.playerInfo().gameMode() == GameMode.SPECTATOR)) {
                    user.invalidate();
                }
                break;
            }
            case DISPLAY_NAME:
            case UPDATE_LIST_ORDER: {
                final PlayerListUser user = this.playerList.getUser(event.playerInfo());
                if (user != null) {
                    user.invalidate();
                }
                if (type == PlayerInfoUpdateEvent.UpdateType.UPDATE_LIST_ORDER) {
                    this.playerList.needSorting();
                    break;
                }
                break;
            }
        }
    }
    
    @Subscribe
    public void onResourceReload(final ResourceReloadEvent event) {
        for (final PlayerListUser user : this.playerList.getUsers()) {
            user.refresh();
        }
    }
    
    @Subscribe
    public void onPlayerInfoRemove(final PlayerInfoRemoveEvent event) {
        final NetworkPlayerInfo info = event.playerInfo();
        if (!info.isListed()) {
            return;
        }
        final PlayerListUser user = this.playerList.getUser(info);
        if (user != null) {
            this.playerList.removeUser(user);
        }
    }
    
    @Subscribe
    public void onPlayerListUpdate(final PlayerListUpdateEvent event) {
        for (final PlayerListUser user : this.playerList.getUsers()) {
            user.invalidate();
        }
    }
    
    @Subscribe
    public void onPlayerListUserUpdate(final PlayerListUserUpdateEvent event) {
        final PlayerListUser user = this.playerList.getUser(event.playerInfo());
        if (user != null) {
            user.invalidate();
        }
    }
    
    @Subscribe
    public void onServerBanner(final ServerBannerEvent event) {
        this.playerList.renderer.updateServerBanner(event.getUrl(), event.getHash());
    }
    
    @Subscribe
    public void onThemeChange(final ThemeChangeEvent event) {
        if (event.phase() == Phase.POST) {
            this.playerList.updateTheme();
        }
    }
    
    @Subscribe
    public void onThemeUpdate(final ThemeUpdateEvent event) {
        if (event.reason().isChangingDimensions()) {
            this.playerList.updateTheme();
        }
    }
    
    @Subscribe
    public void onServerSwitch(final SubServerSwitchEvent event) {
        this.resetTabList();
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.resetTabList();
    }
    
    @Subscribe
    public void onServerLogin(final ServerLoginEvent event) {
        this.resetTabList();
    }
    
    private void resetTabList() {
        this.playerList.users.clear();
        this.playerList.renderer.updateServerBanner(null, null);
    }
}
