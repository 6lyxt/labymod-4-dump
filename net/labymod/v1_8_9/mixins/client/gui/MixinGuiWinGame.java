// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ayc.class })
public class MixinGuiWinGame
{
    @Inject(method = { "sendRespawnPacket" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fixCreditsInMenus(final CallbackInfo ci) {
        if (ave.A().h == null) {
            ave.A().a((axu)null);
            ci.cancel();
        }
    }
}
