// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.gui.screens.inventory;

import net.labymod.api.util.Pair;
import net.labymod.v1_20_6.client.world.inventory.SlotAccessor;
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

@Mixin({ fpe.class })
public abstract class MixinInventoryScreen extends fow<cqx>
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;
    
    public MixinInventoryScreen(final cqx param0, final cmy param1, final xp param2) {
        super((cpw)param0, param1, param2);
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
        for (final crr slot : ((cqx)this.F()).i) {
            this.getSlotByIndex(animation, slot);
        }
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
    private void labyMod$setOldSurvivalLayoutTexture(final fgt graphics, alf location, final int $$1, final int $$2, final int $$3, final int $$4, final int $$5, final int $$6) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            location = Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation();
        }
        graphics.a(location, $$1, $$2, $$3, $$4, $$5, $$6);
    }
    
    private void labyMod$removeRecipeBook() {
        this.p();
    }
    
    @NotNull
    private crr getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final crr slot) {
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
