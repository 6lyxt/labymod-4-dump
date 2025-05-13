// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player;

import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ClientPlayerTurnEvent extends DefaultCancellable implements Event
{
    private final ClientPlayer clientPlayer;
    private double x;
    private double y;
    
    public ClientPlayerTurnEvent(final ClientPlayer clientPlayer, final double x, final double y) {
        this.clientPlayer = clientPlayer;
        this.x = x;
        this.y = y;
    }
    
    public ClientPlayer clientPlayer() {
        return this.clientPlayer;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
}
