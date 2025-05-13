// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.screens.inventory;

import net.labymod.api.util.Pair;
import net.labymod.v1_17_1.client.world.inventory.SlotAccessor;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.Textures;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import java.util.Iterator;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ecj.class })
public abstract class MixinInventoryScreen extends ecc<bnj>
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;
    
    public MixinInventoryScreen(final bnj param0, final bkd param1, final os param2) {
        super((bmk)param0, param1, param2);
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
        if (animation.removeRecipeBook()) {
            this.labyMod$removeRecipeBook();
        }
        this.labyMod$oldSurvivalLayoutTexture = animation.canUseOldSurvivalInventory();
        for (final bob slot : ((bnj)this.u()).i) {
            this.getSlotByIndex(animation, slot);
        }
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"))
    private void labyMod$setOldSurvivalLayoutTexture(final int slot, final ww location) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            RenderSystem.setShaderTexture(slot, (ww)Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation());
            return;
        }
        RenderSystem.setShaderTexture(slot, location);
    }
    
    private void labyMod$removeRecipeBook() {
        this.k();
    }
    
    @NotNull
    private bob getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final bob slot) {
        final Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.PLAYER, slot.d);
        if (pair == null) {
            return slot;
        }
        final InventorySlotData legacySlot = pair.getFirst();
        final InventorySlotData modernSlot = pair.getSecond();
        if (inventoryLayout.canUseOldSurvivalInventory()) {
            return ((SlotAccessor)slot).setPosition(legacySlot.getX(), legacySlot.getY());
        }
        return ((SlotAccessor)slot).setPosition(modernSlot.getX(), modernSlot.getY());
    }
}
