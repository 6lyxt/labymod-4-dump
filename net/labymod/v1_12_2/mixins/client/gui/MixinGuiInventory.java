// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.util.Pair;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Textures;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import java.util.Iterator;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bmx.class })
public abstract class MixinGuiInventory extends bmg
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;
    
    public MixinGuiInventory(final afr lvt_1_1_) {
        super(lvt_1_1_);
    }
    
    @Insert(method = { "initGui()V" }, at = @At("TAIL"))
    private void labyMod$init(final InsertInfo info) {
        if (this.labyMod$inventorySlotRegistry == null) {
            this.labyMod$inventorySlotRegistry = LabyMod.references().inventorySlotRegistry();
        }
        final InventoryLayoutOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("inventory_layout");
        if (animation == null) {
            return;
        }
        if (animation.removeRecipeBook()) {
            this.labyMod$removeRecipeBook();
        }
        this.labyMod$oldSurvivalLayoutTexture = animation.canUseOldSurvivalInventory();
        for (final agr slot : this.h.c) {
            this.getSlotByIndex(animation, slot);
        }
    }
    
    @Redirect(method = { "drawGuiContainerBackgroundLayer" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void labyMod$setOldSurvivalLayoutTexture(final cdr instance, final nf location) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            instance.a((nf)Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation());
            return;
        }
        instance.a(location);
    }
    
    private void labyMod$removeRecipeBook() {
        this.n.clear();
    }
    
    @NotNull
    private agr getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final agr slot) {
        final Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.PLAYER, slot.e);
        if (pair == null) {
            return slot;
        }
        final InventorySlotData legacySlot = pair.getFirst();
        final InventorySlotData modernSlot = pair.getSecond();
        if (inventoryLayout.canUseOldSurvivalInventory()) {
            slot.f = legacySlot.getX();
            slot.g = legacySlot.getY();
        }
        else {
            slot.f = modernSlot.getX();
            slot.g = modernSlot.getY();
        }
        return slot;
    }
}
