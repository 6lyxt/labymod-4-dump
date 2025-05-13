// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.player;

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
    private final fjf wrapped;
    private final Component usernameComponent;
    private Metadata metadata;
    
    public VersionedNetworkPlayerInfo(final fjf wrapped) {
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
        return this.wrapped.f();
    }
    
    @Override
    public Component displayName() {
        final tl displayName = this.wrapped.i();
        if (displayName == null) {
            final ScoreboardTeam team = this.getTeam();
            return (team == null) ? this.usernameComponent : team.formatDisplayName(this.usernameComponent.copy());
        }
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.decorateComponent(this.wrapped, displayName.e()));
    }
    
    @Override
    public GameMode gameMode() {
        if (this.wrapped.e() == null) {
            return GameMode.UNKNOWN;
        }
        return switch (this.wrapped.e()) {
            default -> throw new MatchException(null, null);
            case a -> GameMode.SURVIVAL;
            case b -> GameMode.CREATIVE;
            case c -> GameMode.ADVENTURE;
            case d -> GameMode.SPECTATOR;
        };
    }
    
    @Override
    public <T> T getMinecraftInfo() {
        return (T)this.wrapped;
    }
    
    @Override
    public PlayerSkin getSkin() {
        return (PlayerSkin)this.wrapped.g();
    }
    
    @Override
    public boolean isListed() {
        final fiy connection = eqv.O().J();
        return connection == null || connection.m().contains(this.wrapped);
    }
    
    public fjf getWrapped() {
        return this.wrapped;
    }
    
    private tl decorateComponent(@NotNull final fjf info, final ty mutableComponent) {
        return (tl)((info.e() == cps.d) ? mutableComponent.a(n.u) : mutableComponent);
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
