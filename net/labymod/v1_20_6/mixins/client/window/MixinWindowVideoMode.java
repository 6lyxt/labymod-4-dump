// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.window;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fnm.class })
public class MixinWindowVideoMode
{
    @Inject(at = { @At("HEAD") }, method = { "removed" })
    private void screenRemoved(final CallbackInfo ci) {
        ffh.Q().aO().g();
    }
}
