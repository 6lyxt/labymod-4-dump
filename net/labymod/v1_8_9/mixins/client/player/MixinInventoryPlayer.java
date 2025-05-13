// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.player;

import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ wm.class })
public class MixinInventoryPlayer implements Inventory
{
    @Shadow
    public zx[] a;
    @Shadow
    public int c;
    
    @Override
    public int getSelectedIndex() {
        return this.c;
    }
    
    @Override
    public void setSelectedIndex(final int index) {
        this.c = index;
    }
    
    @Override
    public ItemStack itemStackAt(final int index) {
        try {
            final zx itemStack = this.a[index];
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(MinecraftUtil.AIR);
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        throw new UnsupportedOperationException("Not implemented in this version");
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final zx itemStack : this.a) {
            if (itemStack != null && itemStack.b() == zy.g) {
                found += itemStack.b;
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final zx itemStack : this.a) {
            if (itemStack != null && itemStack.b() == zy.g) {
                return MinecraftUtil.fromMinecraft(itemStack);
            }
        }
        return null;
    }
}
