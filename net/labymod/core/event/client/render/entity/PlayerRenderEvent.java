// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render.entity;

import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;

public class PlayerRenderEvent implements Event
{
    private final Phase phase;
    private final Stack stack;
    private final Player player;
    private final PlayerModel playerModel;
    private final float partialTicks;
    private final int packedLightCoords;
    
    public PlayerRenderEvent(final Phase phase, final Stack stack, final Player player, final PlayerModel playerModel, final float partialTicks, final int packedLightCoords) {
        this.phase = phase;
        this.stack = stack;
        this.player = player;
        this.playerModel = playerModel;
        this.partialTicks = partialTicks;
        this.packedLightCoords = packedLightCoords;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public Player player() {
        return this.player;
    }
    
    public PlayerModel playerModel() {
        return this.playerModel;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public int getPackedLightCoords() {
        return this.packedLightCoords;
    }
    
    public enum Phase
    {
        BEFORE, 
        AFTER;
    }
}
