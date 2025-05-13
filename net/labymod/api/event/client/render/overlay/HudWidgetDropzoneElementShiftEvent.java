// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.overlay;

import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class HudWidgetDropzoneElementShiftEvent extends DefaultCancellable implements Event
{
    private final boolean isOffHandSide;
    private final ItemStack itemStack;
    
    public HudWidgetDropzoneElementShiftEvent(final boolean isOffHandSide, final ItemStack itemStack) {
        this.isOffHandSide = isOffHandSide;
        this.itemStack = itemStack;
    }
    
    public boolean isOffHandSide() {
        return this.isOffHandSide;
    }
    
    public ItemStack itemStack() {
        return this.itemStack;
    }
}
