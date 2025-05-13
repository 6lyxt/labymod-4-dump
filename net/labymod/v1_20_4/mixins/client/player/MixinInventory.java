// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_20_4.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_20_4.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ cfh.class })
public class MixinInventory implements Inventory
{
    @Shadow
    @Final
    private List<iq<cmy>> o;
    @Shadow
    public int l;
    @Shadow
    @Final
    public iq<cmy> i;
    
    @Override
    public int getSelectedIndex() {
        return this.l;
    }
    
    @Override
    public void setSelectedIndex(final int index) {
        this.l = index;
    }
    
    @Override
    public ItemStack itemStackAt(final int index) {
        try {
            final cmy itemStack = (cmy)this.i.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(cnb.a.am_());
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        final cmy stack = MinecraftUtil.toMinecraft(itemStack);
        final fnz gameMode = evi.O().q;
        final fsj player = evi.O().s;
        if (gameMode == null || player == null || !player.fT().d || stack.b()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.i.set(index, (Object)stack);
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final iq<cmy> list : this.o) {
            for (final cmy stack : list) {
                if (stack.a(asp.at)) {
                    found += stack.L();
                }
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final iq<cmy> list : this.o) {
            for (final cmy stack : list) {
                if (stack.a(asp.at)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/world/entity/player/Player;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> iq<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "removeItem(II)Lnet/minecraft/world/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cmy removeItem(final List<cmy> list, final int index, final int amount) {
        final cmy itemStack = bjv.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
