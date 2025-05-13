// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.metadata.Metadata;
import net.labymod.core.client.network.DefaultNetworkPlayerInfo;

public class VersionedNetworkPlayerInfo extends DefaultNetworkPlayerInfo
{
    private final bdc wrapped;
    private Metadata metadata;
    
    public VersionedNetworkPlayerInfo(final bdc wrapped) {
        this.wrapped = wrapped;
        this.metadata = Metadata.create();
    }
    
    @Override
    public GameProfile profile() {
        return (GameProfile)this.wrapped.a();
    }
    
    @Override
    public int getCurrentPing() {
        return this.wrapped.c();
    }
    
    @Override
    public Component displayName() {
        final eu displayName = this.wrapped.k();
        if (displayName != null) {
            return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(displayName);
        }
        final TextComponent username = Component.text(this.profile().getUsername());
        final ScoreboardTeam team = this.getTeam();
        if (team != null) {
            return team.formatDisplayName(username);
        }
        return username;
    }
    
    @Override
    public GameMode gameMode() {
        if (this.wrapped.b() == null) {
            return GameMode.UNKNOWN;
        }
        switch (this.wrapped.b()) {
            case a: {
                return GameMode.UNKNOWN;
            }
            case b: {
                return GameMode.SURVIVAL;
            }
            case c: {
                return GameMode.CREATIVE;
            }
            case d: {
                return GameMode.ADVENTURE;
            }
            case e: {
                return GameMode.SPECTATOR;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.wrapped.b()));
            }
        }
    }
    
    public bdc getWrapped() {
        return this.wrapped;
    }
    
    @Override
    public PlayerSkin getSkin() {
        return (PlayerSkin)this.wrapped;
    }
    
    @Contract("_, _ -> new")
    @NotNull
    private eu formatPlayerName(final auq team, final String username) {
        return (eu)((team == null) ? new fa(username) : new fa(team.d(username)));
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
    
    @Override
    public <T> T getMinecraftInfo() {
        return (T)this.wrapped;
    }
}
