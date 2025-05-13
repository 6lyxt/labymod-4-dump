// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player;

import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Event;

record ClientPlayerAbilitiesUpdateEvent(ClientPlayer clientPlayer) implements Event {
    public PlayerAbilities abilities() {
        return this.clientPlayer.abilities();
    }
}
