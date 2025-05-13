// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui.screens.inventory;

import net.labymod.v1_16_5.client.world.inventory.SlotAccessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.Pair;
import java.util.Iterator;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dqc.class })
public abstract class MixinCreativeModeInventoryScreen extends dqe<dqc.b>
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldCreativeLayoutTexture;
    private InventoryLayoutOldAnimation labyMod$inventoryLayout;
    private int labyMod$entityXShift;
    private int labyMod$entityYShift;
    
    public MixinCreativeModeInventoryScreen(final dqc.b pickerMenu, final bfv inventory, final nr component) {
        super((bic)pickerMenu, inventory, component);
    }
    
    @Insert(method = { "init()V" }, at = @At("TAIL"))
    private void labyMod$init(final InsertInfo info) {
        if (this.labyMod$inventorySlotRegistry == null) {
            this.labyMod$inventorySlotRegistry = LabyMod.references().inventorySlotRegistry();
        }
        final InventoryLayoutOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("inventory_layout");
        if (animation == null) {
            return;
        }
        this.labyMod$inventoryLayout = animation;
        this.labyMod$oldCreativeLayoutTexture = animation.canUseOldCreativeLayout();
        this.labyMod$entityXShift = animation.getEntityXShift();
        this.labyMod$entityYShift = animation.getEntityYShift();
        this.labyMod$modifySlots();
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventory(IIIFFLnet/minecraft/world/entity/LivingEntity;)V"))
    private void labyMod$setEntityToOldPosition(final int x, final int y, final int scale, final float mouseX, final float mouseY, final aqm entity) {
        if (this.labyMod$oldCreativeLayoutTexture) {
            dql.a(x - this.labyMod$entityXShift, y - this.labyMod$entityYShift, scale, mouseX - this.labyMod$entityXShift, mouseY - this.labyMod$entityYShift, entity);
            return;
        }
        dql.a(x, y, scale, mouseX, mouseY, entity);
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bind(Lnet/minecraft/resources/ResourceLocation;)V", ordinal = 1))
    private void labyMod$setOldCreativeTexture(final ekd instance, final vk location) {
        if (!this.labyMod$oldCreativeLayoutTexture) {
            instance.a(location);
            return;
        }
        final String path = location.a();
        final int lastIndex = path.lastIndexOf(47);
        String name = path.substring(lastIndex);
        final int firstIndex = name.indexOf(95);
        name = name.substring(firstIndex + 1);
        name = name.substring(0, name.length() - ".png".length());
        ResourceLocation resourceLocation = null;
        if (name.equals("inventory")) {
            resourceLocation = Textures.CREATIVE_INVENTORY_TAB_INVENTORY;
        }
        if (name.equals("item_search")) {
            resourceLocation = Textures.CREATIVE_INVENTORY_TAB_ITEM_SEARCH;
        }
        if (name.equals("items")) {
            resourceLocation = Textures.CREATIVE_INVENTORY_TAB_ITEMS;
        }
        if (resourceLocation != null) {
            instance.a((vk)resourceLocation.getMinecraftLocation());
        }
    }
    
    @Inject(method = { "selectTab" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void labyMod$onSelectTab(final bks tab, final CallbackInfo ci) {
        this.labyMod$modifySlots();
    }
    
    private void labyMod$modifySlots() {
        if (this.labyMod$oldCreativeLayoutTexture) {
            for (final bjr slot : ((dqc.b)this.h()).a) {
                final Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.CREATIVE, slot.e, slot.f);
                if (pair == null) {
                    continue;
                }
                this.getSlotByIndex(this.labyMod$inventoryLayout, pair.getFirst(), slot);
            }
        }
    }
    
    @NotNull
    private bjr getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final InventorySlotData legacySlot, @NotNull final bjr slot) {
        if (!inventoryLayout.canUseOldCreativeLayout()) {
            return slot;
        }
        return ((SlotAccessor)slot).setPosition(legacySlot.getX(), legacySlot.getY());
    }
}
