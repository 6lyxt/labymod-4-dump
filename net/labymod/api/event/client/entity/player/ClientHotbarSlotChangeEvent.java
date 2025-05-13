// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player;

import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ClientHotbarSlotChangeEvent extends DefaultCancellable implements Event
{
    private final int fromSlot;
    private int toSlot;
    private int delta;
    
    public ClientHotbarSlotChangeEvent(final int fromSlot, final int delta) {
        this.fromSlot = fromSlot;
        this.toSlot = (fromSlot - delta) % 9;
        if (this.toSlot < 0) {
            this.toSlot += 9;
        }
        this.delta = delta;
    }
    
    public int fromSlot() {
        return this.fromSlot;
    }
    
    public int toSlot() {
        return this.toSlot;
    }
    
    public void setToSlot(final int toSlot) {
        this.toSlot = toSlot;
    }
    
    public int delta() {
        return this.delta;
    }
    
    public void setDelta(final int delta) {
        this.delta = delta;
    }
}
