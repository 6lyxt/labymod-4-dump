// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ axv.class })
public class MixinGuiSelectWorld
{
    @Shadow
    private boolean i;
    
    @Inject(method = { "initGui" }, at = { @At("TAIL") })
    public void labyMod$allowWorldJoin(final CallbackInfo callbackInfo) {
        this.i = false;
    }
}
