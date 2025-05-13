// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;

public class ItemStackTooltipEvent implements Event
{
    private final ItemStack itemStack;
    private final List<Component> tooltipLines;
    private final TooltipType type;
    private final boolean creative;
    
    public ItemStackTooltipEvent(@NotNull final ItemStack itemStack, @NotNull final List<Component> tooltipLines, @NotNull final TooltipType type, final boolean creative) {
        this.itemStack = itemStack;
        this.tooltipLines = tooltipLines;
        this.type = type;
        this.creative = creative;
    }
    
    @NotNull
    public ItemStack itemStack() {
        return this.itemStack;
    }
    
    @NotNull
    public List<Component> getTooltipLines() {
        return this.tooltipLines;
    }
    
    @NotNull
    public TooltipType type() {
        return this.type;
    }
    
    public boolean isCreative() {
        return this.creative;
    }
    
    public enum TooltipType
    {
        NORMAL, 
        ADVANCED;
    }
}
