// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.minecraft;

import java.util.UUID;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.client.entity.player.DamageBlockedEvent;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.Laby;
import java.util.HashSet;
import java.util.Set;

public class MinecraftItemGameDetails
{
    private final Set<DamagePlayer> damagedPlayers;
    
    public MinecraftItemGameDetails() {
        this.damagedPlayers = new HashSet<DamagePlayer>();
        Laby.references().eventBus().registerListener(this);
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        final Iterator<DamagePlayer> iterator = this.damagedPlayers.iterator();
        while (iterator.hasNext()) {
            final DamagePlayer damagePlayer = iterator.next();
            if (!damagePlayer.isAccessed()) {
                continue;
            }
            iterator.remove();
        }
    }
    
    @Subscribe
    public void onDamageBlocked(final DamageBlockedEvent event) {
        final Player player = event.player();
        this.damagedPlayers.add(new DamagePlayer(player));
    }
    
    public boolean blockedDamage(final Player player) {
        for (final DamagePlayer damagedPlayer : this.damagedPlayers) {
            final UUID uniqueId = player.getUniqueId();
            if (uniqueId.equals(damagedPlayer.getUuid())) {
                damagedPlayer.setAccessed(true);
                return true;
            }
        }
        return false;
    }
    
    public static class DamagePlayer
    {
        private final Player player;
        private boolean accessed;
        
        public DamagePlayer(final Player player) {
            this.accessed = false;
            this.player = player;
        }
        
        public Player getPlayer() {
            return this.player;
        }
        
        public UUID getUuid() {
            return this.player.getUniqueId();
        }
        
        public boolean isAccessed() {
            return this.accessed;
        }
        
        public void setAccessed(final boolean accessed) {
            this.accessed = accessed;
        }
    }
}
