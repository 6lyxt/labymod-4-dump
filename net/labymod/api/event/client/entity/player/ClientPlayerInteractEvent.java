// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player;

import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ClientPlayerInteractEvent extends DefaultCancellable implements Event
{
    private final ClientPlayer clientPlayer;
    private final InteractionType interactionType;
    
    public ClientPlayerInteractEvent(final ClientPlayer clientPlayer, final InteractionType interactionType) {
        this.clientPlayer = clientPlayer;
        this.interactionType = interactionType;
    }
    
    public ClientPlayer clientPlayer() {
        return this.clientPlayer;
    }
    
    public InteractionType type() {
        return this.interactionType;
    }
    
    public enum InteractionType
    {
        INTERACT, 
        ATTACK, 
        CONTINUE_ATTACK, 
        PICK_BLOCK;
    }
}
