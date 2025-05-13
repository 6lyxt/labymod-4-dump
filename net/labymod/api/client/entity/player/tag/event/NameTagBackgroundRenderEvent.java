// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.event;

import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class NameTagBackgroundRenderEvent extends DefaultCancellable implements Event
{
    private static final NameTagBackgroundRenderEvent EVENT;
    private int color;
    
    public NameTagBackgroundRenderEvent() {
        this.color = 0;
    }
    
    public static NameTagBackgroundRenderEvent singleton() {
        NameTagBackgroundRenderEvent.EVENT.setColor(0);
        NameTagBackgroundRenderEvent.EVENT.setCancelled(false);
        return NameTagBackgroundRenderEvent.EVENT;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    static {
        EVENT = new NameTagBackgroundRenderEvent();
    }
}
