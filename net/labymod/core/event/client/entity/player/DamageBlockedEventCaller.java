// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.entity.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.DamageBlockedEvent;
import net.labymod.api.client.entity.player.Player;

public final class DamageBlockedEventCaller
{
    public static void fireDamageBlocked(final Player player, final float damageValue) {
        Laby.fireEvent(new DamageBlockedEvent(player, damageValue));
    }
}
