// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import java.util.List;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_21_5.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ csh.class })
public class MixinInventory implements Inventory
{
    @Shadow
    public int j;
    @Shadow
    @Final
    public jp<dak> i;
    
    @Override
    public int getSelectedIndex() {
        return this.j;
    }
    
    @Override
    public void setSelectedIndex(final int index) {
        this.j = index;
    }
    
    @Override
    public ItemStack itemStackAt(final int index) {
        try {
            final dak itemStack = (dak)this.i.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(dao.a.m());
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        final dak stack = MinecraftUtil.toMinecraft(itemStack);
        final gly gameMode = fqq.Q().r;
        final gqm player = fqq.Q().t;
        if (gameMode == null || player == null || !player.gk().d || stack.f()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.i.set(index, (Object)stack);
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final dak stack : this.i) {
            if (stack.a(axv.aZ)) {
                found += stack.M();
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final dak stack : this.i) {
            if (stack.a(axv.aZ)) {
                return MinecraftUtil.fromMinecraft(stack);
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> jp<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "removeItem(II)Lnet/minecraft/world/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public dak removeItem(final List<dak> list, final int index, final int amount) {
        final dak itemStack = buw.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
