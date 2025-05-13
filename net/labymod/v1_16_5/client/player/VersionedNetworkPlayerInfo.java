// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.player;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.Laby;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.component.Component;
import net.labymod.core.client.network.DefaultNetworkPlayerInfo;

public class VersionedNetworkPlayerInfo extends DefaultNetworkPlayerInfo
{
    private final dwx wrapped;
    private final Component usernameComponent;
    private Metadata metadata;
    
    public VersionedNetworkPlayerInfo(final dwx wrapped) {
        this.wrapped = wrapped;
        this.usernameComponent = Component.text(this.profile().getUsername());
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
        final nr displayName = this.wrapped.l();
        if (displayName == null) {
            final ScoreboardTeam team = this.getTeam();
            return (team == null) ? this.usernameComponent : team.formatDisplayName(this.usernameComponent.copy());
        }
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.decorateComponent(this.wrapped, displayName.e()));
    }
    
    @Override
    public GameMode gameMode() {
        if (this.wrapped.b() == null) {
            return GameMode.UNKNOWN;
        }
        switch (this.wrapped.b()) {
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
                return GameMode.UNKNOWN;
            }
        }
    }
    
    @Override
    public <T> T getMinecraftInfo() {
        return (T)this.wrapped;
    }
    
    @Override
    public PlayerSkin getSkin() {
        return (PlayerSkin)this.wrapped;
    }
    
    public dwx getWrapped() {
        return this.wrapped;
    }
    
    private nr decorateComponent(@NotNull final dwx info, final nx mutableComponent) {
        return (nr)((info.b() == bru.e) ? mutableComponent.a(k.u) : mutableComponent);
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
}
