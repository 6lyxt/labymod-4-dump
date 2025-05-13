// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.badge;

import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface BadgeRegistry
{
    int render(final Stack p0, final PositionType p1, final float p2, final float p3, final NetworkPlayerInfo p4);
    
    int getWidth(final PositionType p0, final NetworkPlayerInfo p1, final boolean p2);
    
    void beginRender(final Stack p0, final PositionType p1);
    
    void endRender(final Stack p0, final PositionType p1);
    
    void register(final String p0, final PositionType p1, final BadgeRenderer p2);
    
    void registerAfter(final String p0, final String p1, final PositionType p2, final BadgeRenderer p3);
    
    void registerBefore(final String p0, final String p1, final PositionType p2, final BadgeRenderer p3);
    
    void unregister(final String p0);
    
    default int getWidth(final PositionType type, final NetworkPlayerInfo player) {
        return this.getWidth(type, player, true);
    }
}
