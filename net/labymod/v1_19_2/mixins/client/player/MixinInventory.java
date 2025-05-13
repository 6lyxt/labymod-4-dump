// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_19_2.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_19_2.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ bub.class })
public class MixinInventory implements Inventory
{
    @Shadow
    @Final
    private List<hi<cax>> n;
    @Shadow
    public int k;
    @Shadow
    @Final
    public hi<cax> h;
    
    @Override
    public int getSelectedIndex() {
        return this.k;
    }
    
    @Override
    public void setSelectedIndex(final int index) {
        this.k = index;
    }
    
    @Override
    public ItemStack itemStackAt(final int index) {
        try {
            final cax itemStack = (cax)this.h.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(caz.a.Q_());
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        final cax stack = MinecraftUtil.toMinecraft(itemStack);
        final euy gameMode = efu.I().r;
        final eyw player = efu.I().t;
        if (gameMode == null || player == null || !player.fB().d || stack.b()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.h.set(index, (Object)stack);
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final hi<cax> list : this.n) {
            for (final cax stack : list) {
                if (stack.a(aks.ap)) {
                    found += stack.K();
                }
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final hi<cax> list : this.n) {
            for (final cax stack : list) {
                if (stack.a(aks.ap)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/world/entity/player/Player;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> hi<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "removeItem(II)Lnet/minecraft/world/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cax removeItem(final List<cax> list, final int index, final int amount) {
        final cax itemStack = bad.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
