// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_16_5.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ bfv.class })
public class MixinInventory implements Inventory
{
    @Shadow
    @Final
    private List<gj<bmb>> f;
    @Shadow
    public int d;
    @Shadow
    @Final
    public gj<bmb> a;
    
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
            final bmb itemStack = (bmb)this.a.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(bmd.a.r());
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        final bmb stack = MinecraftUtil.toMinecraft(itemStack);
        final dww gameMode = djz.C().q;
        final dzm player = djz.C().s;
        if (gameMode == null || player == null || !player.bC.d || stack.a()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.a.set(index, (Object)stack);
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final gj<bmb> list : this.f) {
            for (final bmb stack : list) {
                if (stack.b().a((ael)aeg.Y)) {
                    found += stack.E();
                }
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final gj<bmb> list : this.f) {
            for (final bmb stack : list) {
                if (stack.b().a((ael)aeg.Y)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/world/entity/player/Player;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> gj<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "removeItem(II)Lnet/minecraft/world/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public bmb removeItem(final List<bmb> list, final int index, final int amount) {
        final bmb itemStack = aoo.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
