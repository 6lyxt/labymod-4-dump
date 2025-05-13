// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.gui.screens.inventory;

import net.labymod.v1_21_5.client.world.inventory.SlotAccessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.Pair;
import java.util.Iterator;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import java.util.function.Function;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gav.class })
public abstract class MixinCreativeModeInventoryScreen extends gaf<gav.b>
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldCreativeLayoutTexture;
    private InventoryLayoutOldAnimation labyMod$inventoryLayout;
    private int labyMod$entityXShift;
    private int labyMod$entityYShift;
    
    public MixinCreativeModeInventoryScreen(final gav.b pickerMenu, final csh inventory, final xg component) {
        super((cwb)pickerMenu, inventory, component);
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
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphics;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"))
    private void labyMod$setEntityToOldPosition(final ftk graphics, final int x, final int y, final int width, final int height, final int scale, final float modelHeight, final float mouseX, final float mouseY, final byf entity) {
        if (this.labyMod$oldCreativeLayoutTexture) {
            gbg.a(graphics, x - this.labyMod$entityXShift, y - this.labyMod$entityYShift, width, height, scale, modelHeight, mouseX - this.labyMod$entityXShift, mouseY - this.labyMod$entityYShift, entity);
            return;
        }
        gbg.a(graphics, x, y, width, height, scale, modelHeight, mouseX, mouseY, entity);
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 0))
    private void labyMod$setOldCreativeTexture(final ftk graphics, final Function<alr, gry> renderTypeMapper, final alr location, final int $$1, final int $$2, final float $$3, final float $$4, final int $$5, final int $$6, final int $$7, final int $$8) {
        if (!this.labyMod$oldCreativeLayoutTexture) {
            graphics.a((Function)renderTypeMapper, location, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
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
            graphics.a((Function)renderTypeMapper, (alr)resourceLocation.getMinecraftLocation(), $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
        }
    }
    
    @Inject(method = { "selectTab" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;add(Ljava/lang/Object;)Z", ordinal = 3, shift = At.Shift.AFTER) })
    private void labyMod$onSelectTab(final czb tab, final CallbackInfo ci) {
        this.labyMod$modifySlots();
    }
    
    private void labyMod$modifySlots() {
        if (this.labyMod$oldCreativeLayoutTexture) {
            for (final cxz slot : ((gav.b)this.F()).k) {
                final Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.CREATIVE, slot.e, slot.f);
                if (pair == null) {
                    continue;
                }
                this.getSlotByIndex(this.labyMod$inventoryLayout, pair.getFirst(), slot);
            }
        }
    }
    
    @NotNull
    private cxz getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final InventorySlotData legacySlot, @NotNull final cxz slot) {
        if (!inventoryLayout.canUseOldCreativeLayout()) {
            return slot;
        }
        return ((SlotAccessor)slot).setPosition(legacySlot.getX(), legacySlot.getY());
    }
}
