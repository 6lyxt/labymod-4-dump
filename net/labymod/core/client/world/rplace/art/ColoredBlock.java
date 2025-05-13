// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.rplace.art;

import net.labymod.api.Laby;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.world.item.ItemStack;

public class ColoredBlock
{
    private final String identifier;
    private final int color;
    private final int invertedColor;
    private ItemStack itemStack;
    
    public ColoredBlock(final String identifier, final int color) {
        this.identifier = identifier;
        this.color = color;
        this.invertedColor = ColorUtil.invertColor(color);
        try {
            final String[] segments = identifier.split(":");
            final ItemStack itemStack = Laby.references().itemStackFactory().create(segments[0], segments[1]);
            if (!itemStack.isAir()) {
                this.itemStack = itemStack;
            }
        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public int getInvertedColor() {
        return this.invertedColor;
    }
    
    public boolean isValid() {
        return this.itemStack != null;
    }
}
