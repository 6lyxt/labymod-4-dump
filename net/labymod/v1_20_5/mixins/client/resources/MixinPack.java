// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_20_5.client.resources.pack.WrappedPackResources;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ atx.class })
public class MixinPack
{
    @Inject(method = { "open" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$wrapPackResources(final CallbackInfoReturnable<atb> cir) {
        cir.setReturnValue((Object)new WrappedPackResources((atb)cir.getReturnValue()));
    }
}
