// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import java.util.List;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_12_2.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ aec.class })
public class MixinInventoryPlayer implements Inventory
{
    @Shadow
    public int d;
    @Shadow
    @Final
    public fi<aip> a;
    
    @Override
    public int getSelectedIndex() {
        return this.d;
    }
    
    @Override
    public void setSelectedIndex(final int index) {
        this.d = index;
    }
    
    @Override
    public ItemStack itemStackAt(final int index) {
        try {
            final aip itemStack = (aip)this.a.get(index);
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
        for (final aip itemStack : this.a) {
            if (itemStack != null && itemStack.c() == air.h) {
                found += itemStack.E();
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final aip itemStack : this.a) {
            if (itemStack != null && itemStack.c() == air.h) {
                return MinecraftUtil.fromMinecraft(itemStack);
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/entity/player/EntityPlayer;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/util/NonNullList;"))
    public <E> fi<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "decrStackSize(II)Lnet/minecraft/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ItemStackHelper;getAndSplit(Ljava/util/List;II)Lnet/minecraft/item/ItemStack;"))
    public aip removeItem(final List<aip> list, final int index, final int amount) {
        final aip itemStack = tw.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
