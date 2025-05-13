// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect;

import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.entity.player.Player;

@FunctionalInterface
public interface GeometryEffectApplier
{
    void apply(final Player p0, final ItemMetadata p1, final ItemEffect.EffectData p2);
}
