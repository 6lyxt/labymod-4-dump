// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.gui.screens.inventory;

import net.labymod.v1_19_2.client.world.inventory.SlotAccessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.Pair;
import java.util.Iterator;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ emx.class })
public abstract class MixinCreativeModeInventoryScreen extends emz<emx.b>
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldCreativeLayoutTexture;
    private InventoryLayoutOldAnimation labyMod$inventoryLayout;
    private int labyMod$entityXShift;
    private int labyMod$entityYShift;
    
    public MixinCreativeModeInventoryScreen(final emx.b pickerMenu, final bub inventory, final rq component) {
        super((bwm)pickerMenu, inventory, component);
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
    private void labyMod$setEntityToOldPosition(final int x, final int y, final int scale, final float mouseX, final float mouseY, final bcc entity) {
        if (this.labyMod$oldCreativeLayoutTexture) {
            eng.a(x - this.labyMod$entityXShift, y - this.labyMod$entityYShift, scale, mouseX - this.labyMod$entityXShift, mouseY - this.labyMod$entityYShift, entity);
            return;
        }
        eng.a(x, y, scale, mouseX, mouseY, entity);
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", ordinal = 1))
    private void labyMod$setOldCreativeTexture(final int slot, final abb location) {
        if (!this.labyMod$oldCreativeLayoutTexture) {
            RenderSystem.setShaderTexture(slot, location);
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
            RenderSystem.setShaderTexture(slot, (abb)resourceLocation.getMinecraftLocation());
        }
    }
    
    @Inject(method = { "selectTab" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;add(Ljava/lang/Object;)Z", ordinal = 3, shift = At.Shift.AFTER) })
    private void labyMod$onSelectTab(final bzj tab, final CallbackInfo ci) {
        this.labyMod$modifySlots();
    }
    
    private void labyMod$modifySlots() {
        if (this.labyMod$oldCreativeLayoutTexture) {
            for (final byd slot : ((emx.b)this.k()).i) {
                final Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.CREATIVE, slot.f, slot.g);
                if (pair == null) {
                    continue;
                }
                this.getSlotByIndex(this.labyMod$inventoryLayout, pair.getFirst(), slot);
            }
        }
    }
    
    @NotNull
    private byd getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final InventorySlotData legacySlot, @NotNull final byd slot) {
        if (!inventoryLayout.canUseOldCreativeLayout()) {
            return slot;
        }
        return ((SlotAccessor)slot).setPosition(legacySlot.getX(), legacySlot.getY());
    }
}
