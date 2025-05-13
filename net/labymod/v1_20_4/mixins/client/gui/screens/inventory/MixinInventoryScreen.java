// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.gui.screens.inventory;

import net.labymod.api.util.Pair;
import net.labymod.v1_20_4.client.world.inventory.SlotAccessor;
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

@Mixin({ ffa.class })
public abstract class MixinInventoryScreen extends fes<cjc>
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;
    
    public MixinInventoryScreen(final cjc param0, final cfh param1, final vf param2) {
        super((cib)param0, param1, param2);
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
        for (final cjw slot : ((cjc)this.E()).i) {
            this.getSlotByIndex(animation, slot);
        }
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
    private void labyMod$setOldSurvivalLayoutTexture(final ewu graphics, ahg location, final int $$1, final int $$2, final int $$3, final int $$4, final int $$5, final int $$6) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            location = Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation();
        }
        graphics.a(location, $$1, $$2, $$3, $$4, $$5, $$6);
    }
    
    private void labyMod$removeRecipeBook() {
        this.q();
    }
    
    @NotNull
    private cjw getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final cjw slot) {
        final Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.PLAYER, slot.e);
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
