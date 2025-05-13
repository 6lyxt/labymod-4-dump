// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import java.util.Comparator;
import net.labymod.core.client.network.DefaultNetworkPlayerInfo;
import java.util.UUID;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.function.Consumer;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import java.util.Iterator;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;

@Link("activity/player-list.lss")
@AutoActivity
public class PlayerListOverlay extends IngameOverlayActivity
{
    public final PlayerListListener listener;
    public final PlayerListRenderer renderer;
    public final CachedComponent header;
    public final CachedComponent footer;
    public final List<PlayerListUser> users;
    private boolean needsSorting;
    private boolean needsUpdate;
    private long timeLastNotVisible;
    private boolean isDoubleTapped;
    private boolean wasVisibleLastFrame;
    
    public PlayerListOverlay() {
        this.header = new CachedComponent();
        this.footer = new CachedComponent();
        this.users = new ArrayList<PlayerListUser>();
        this.listener = new PlayerListListener(this);
        this.renderer = new PlayerListRenderer(this);
        this.onOpenScreen();
        this.updateTheme();
        this.labyAPI.eventBus().registerListener(this.listener);
    }
    
    @Override
    public void render(final ScreenContext context) {
        this.sort();
        final TabList tabList = this.labyAPI.minecraft().getTabList();
        if (tabList == null) {
            this.header.invalidate();
            this.footer.invalidate();
        }
        else {
            final int width = (int)this.bounds().getWidth(BoundsType.INNER) - 50;
            this.header.update(tabList.header(), (float)width);
            this.footer.update(tabList.footer(), (float)width);
        }
        this.renderer.render(context.stack(), this.labyAPI, this.bounds(), this.needsUpdate);
        this.needsUpdate = false;
    }
    
    @Override
    public boolean isVisible() {
        final TabList tabList = this.labyAPI.minecraft().getTabList();
        final boolean visible = tabList != null && tabList.isVisible() && LabyConfigProvider.INSTANCE.get().multiplayer().customPlayerList().get();
        this.updateDoubleTab(visible);
        return visible;
    }
    
    private void updateDoubleTab(final boolean visible) {
        if (visible && !this.wasVisibleLastFrame) {
            final long timePassed = TimeUtil.getMillis() - this.timeLastNotVisible;
            this.isDoubleTapped = (timePassed < 100L);
        }
        if (!visible && this.wasVisibleLastFrame) {
            this.timeLastNotVisible = TimeUtil.getMillis();
        }
        if (this.wasVisibleLastFrame && !visible) {
            this.isDoubleTapped = false;
        }
        this.wasVisibleLastFrame = visible;
    }
    
    @Override
    public void resize(final int width, final int height) {
        super.resize(width, height);
        this.renderer.refreshColumns();
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!this.isVisible()) {
            return;
        }
        for (final PlayerListUser user : this.users) {
            user.tick();
        }
        this.renderer.tick();
    }
    
    @Override
    public int getPriority() {
        return 100;
    }
    
    public void forEachUserFromTeam(final ScoreboardTeam team, final Consumer<PlayerListUser> user) {
        for (final PlayerListUser playerListUser : this.users) {
            if (playerListUser.getTeam() == team) {
                user.accept(playerListUser);
            }
        }
    }
    
    public PlayerListUser getUser(final String userName) {
        for (final PlayerListUser user : this.users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
    
    public PlayerListUser getUser(final NetworkPlayerInfo info) {
        final UUID uniqueId = info.profile().getUniqueId();
        for (final PlayerListUser user : this.users) {
            if (user.uniqueId.equals(uniqueId)) {
                return user;
            }
        }
        return null;
    }
    
    public boolean isDoubleTapped() {
        return this.isDoubleTapped;
    }
    
    public List<PlayerListUser> getUsers() {
        return this.users;
    }
    
    public PlayerListUser addUser(final NetworkPlayerInfo info) {
        PlayerListUser user = this.getUser(info);
        if (user != null) {
            this.removeUser(user);
        }
        user = new PlayerListUser(info);
        this.users.add(user);
        this.needSorting();
        return user;
    }
    
    public void removeUser(final NetworkPlayerInfo playerInfo) {
        this.removeUser(this.getUser(playerInfo));
    }
    
    public void removeUser(final PlayerListUser user) {
        this.users.remove(user);
        this.needUpdate();
    }
    
    public void updateTheme() {
        for (final PlayerListUser user : this.users) {
            user.refresh();
        }
        this.renderer.updateTheme();
        this.header.refresh();
        this.footer.refresh();
    }
    
    public void needSorting() {
        this.needsSorting = true;
        this.needsUpdate = true;
    }
    
    public void needUpdate() {
        this.needsUpdate = true;
    }
    
    private void sort() {
        if (!this.needsSorting) {
            return;
        }
        this.needsSorting = false;
        this.users.sort(DefaultNetworkPlayerInfo.PLAYER_LIST_COMPARATOR);
    }
}
