// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bmr.class })
public class MixinInventoryEffectRenderer
{
    @Shadow
    private boolean v;
    
    @Inject(method = { "updateActivePotionEffects" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/InventoryEffectRenderer;guiLeft:I", ordinal = 0, shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$fixInventoryPosition(final CallbackInfo ci) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().potionFix().get()) {
            ci.cancel();
            this.v = true;
        }
    }
}
