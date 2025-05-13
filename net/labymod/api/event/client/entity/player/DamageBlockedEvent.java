// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Event;

record DamageBlockedEvent(Player player, float damageValue) implements Event {}
