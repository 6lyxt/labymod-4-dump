// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_21_3.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ cpw.class })
public class MixinInventory implements Inventory
{
    @Shadow
    @Final
    private List<jz<cxp>> l;
    @Shadow
    public int j;
    @Shadow
    @Final
    public jz<cxp> g;
    
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
            final cxp itemStack = (cxp)this.g.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(cxt.a.n());
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        final cxp stack = MinecraftUtil.toMinecraft(itemStack);
        final gfu gameMode = fmg.Q().r;
        final gkh player = fmg.Q().t;
        if (gameMode == null || player == null || !player.gj().d || stack.f()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.g.set(index, (Object)stack);
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final jz<cxp> list : this.l) {
            for (final cxp stack : list) {
                if (stack.a(ayd.aZ)) {
                    found += stack.L();
                }
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final jz<cxp> list : this.l) {
            for (final cxp stack : list) {
                if (stack.a(ayd.aZ)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/world/entity/player/Player;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> jz<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "removeItem(II)Lnet/minecraft/world/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cxp removeItem(final List<cxp> list, final int index, final int amount) {
        final cxp itemStack = btd.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
