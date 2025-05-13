// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_20_1.client.player.inventory.InventoryTracker;
import java.util.Iterator;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Inventory;

@Mixin({ byn.class })
public class MixinInventory implements Inventory
{
    @Shadow
    @Final
    private List<hn<cfz>> o;
    @Shadow
    public int l;
    @Shadow
    @Final
    public hn<cfz> i;
    
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
            final cfz itemStack = (cfz)this.i.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        }
        catch (final IndexOutOfBoundsException exception) {
            return MinecraftUtil.fromMinecraft(cgc.a.ae_());
        }
    }
    
    @Override
    public void setCreativeModeItemStack(final int index, final ItemStack itemStack) {
        final cfz stack = MinecraftUtil.toMinecraft(itemStack);
        final ffa gameMode = enn.N().r;
        final fiy player = enn.N().t;
        if (gameMode == null || player == null || !player.fO().d || stack.b()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.i.set(index, (Object)stack);
    }
    
    @Override
    public int countAllArrows() {
        int found = 0;
        for (final hn<cfz> list : this.o) {
            for (final cfz stack : list) {
                if (stack.a(ane.at)) {
                    found += stack.L();
                }
            }
        }
        return found;
    }
    
    @Override
    public ItemStack getNextArrows() {
        for (final hn<cfz> list : this.o) {
            for (final cfz stack : list) {
                if (stack.a(ane.at)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }
    
    @Redirect(method = { "<init>(Lnet/minecraft/world/entity/player/Player;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> hn<E> withSize(final int size, final E object) {
        return InventoryTracker.withSize(size, object, this);
    }
    
    @Redirect(method = { "removeItem(II)Lnet/minecraft/world/item/ItemStack;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cfz removeItem(final List<cfz> list, final int index, final int amount) {
        final cfz itemStack = bdr.a((List)list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, (ItemStack)itemStack));
        return itemStack;
    }
}
