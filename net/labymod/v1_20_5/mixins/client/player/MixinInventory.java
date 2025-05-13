// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_20_5.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_20_5.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ cmx.class })
public class MixinInventory implements Inventory
{
    @Shadow
    @Final
    private List<jr<cuq>> n;
    @Shadow
    public int k;
    @Shadow
    @Final
    public jr<cuq> h;
    
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
            final cuq itemStack = (cuq)this.h.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(cut.a.w());
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        final cuq stack = MinecraftUtil.toMinecraft(itemStack);
        final fyf gameMode = ffg.Q().q;
        final gcr player = ffg.Q().s;
        if (gameMode == null || player == null || !player.gd().d || stack.e()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.h.set(index, (Object)stack);
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final jr<cuq> list : this.n) {
            for (final cuq stack : list) {
                if (stack.a(awy.aU)) {
                    found += stack.I();
                }
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final jr<cuq> list : this.n) {
            for (final cuq stack : list) {
                if (stack.a(awy.aU)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/world/entity/player/Player;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> jr<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "removeItem(II)Lnet/minecraft/world/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cuq removeItem(final List<cuq> list, final int index, final int amount) {
        final cuq itemStack = bqp.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
