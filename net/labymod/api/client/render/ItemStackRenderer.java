// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.Laby;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;

public interface ItemStackRenderer
{
    default void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y) {
        this.renderItemStack(stack, itemStack, x, y, true);
    }
    
    default void renderItemStack(final Stack stack, final ItemStack itemStack, final int x, final int y, final boolean decorate) {
        final float alpha = Laby.labyAPI().renderPipeline().getAlpha();
        if (alpha == 0.0f) {
            return;
        }
        this.renderItemStack(stack, itemStack, x, y, decorate, alpha);
    }
    
    void renderItemStack(final Stack p0, final ItemStack p1, final int p2, final int p3, final boolean p4, final float p5);
}
