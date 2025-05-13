// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.gui.screens.inventory;

import net.labymod.api.util.Pair;
import net.labymod.v1_21_3.client.world.inventory.SlotAccessor;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Textures;
import java.util.function.Function;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import java.util.Iterator;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fvo.class })
public abstract class MixinInventoryScreen extends fun<cuf>
{
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;
    
    public MixinInventoryScreen(final cuf param0, final cpw param1, final xv param2) {
        super((ctc)param0, param1, param2);
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
        for (final cuz slot : ((cuf)this.F()).k) {
            this.getSlotByIndex(animation, slot);
        }
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void labyMod$setOldSurvivalLayoutTexture(final fns graphics, final Function<alz, glv> renderTypeMapper, alz location, final int $$1, final int $$2, final float $$3, final float $$4, final int $$5, final int $$6, final int $$7, final int $$8) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            location = Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation();
        }
        graphics.a((Function)renderTypeMapper, location, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
    }
    
    private void labyMod$removeRecipeBook() {
        this.o();
    }
    
    @NotNull
    private cuz getSlotByIndex(@NotNull final InventoryLayoutOldAnimation inventoryLayout, @NotNull final cuz slot) {
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
