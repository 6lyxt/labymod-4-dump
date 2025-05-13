// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bmg.class })
public abstract class MixinGuiContainer
{
    @Shadow
    protected abstract boolean b(final int p0);
    
    @Inject(method = { "mouseClicked" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;mouseClicked(III)V", shift = At.Shift.AFTER) }, cancellable = true)
    public void labyMod$fixMouseButtonsInInventory(final int lvt_1_1_, final int lvt_2_1_, final int lvt_3_1_, final CallbackInfo ci) {
        if (this.b(lvt_3_1_ - 100)) {
            ci.cancel();
        }
    }
}
