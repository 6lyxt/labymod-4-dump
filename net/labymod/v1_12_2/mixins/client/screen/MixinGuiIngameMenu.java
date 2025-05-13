// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.screen;

import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ blg.class })
public class MixinGuiIngameMenu extends blk
{
    private boolean isConfirmed;
    
    public MixinGuiIngameMenu() {
        this.isConfirmed = false;
    }
    
    @Inject(method = { "initGui" }, at = { @At("HEAD") })
    public void resetConfirmed(final CallbackInfo ci) {
        this.isConfirmed = false;
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isIntegratedServerRunning()Z", shift = At.Shift.BEFORE) }, cancellable = true)
    private void confirmDisconnect(final bja button, final CallbackInfo ci) {
        if (this.j.E() || !LabyMod.getInstance().config().multiplayer().confirmDisconnect().get()) {
            return;
        }
        if (!this.isConfirmed) {
            this.isConfirmed = true;
            button.j = "§c" + cey.a("labymod.activity.menu.button.confirmDisconnect", new Object[0]);
            ci.cancel();
        }
    }
}
