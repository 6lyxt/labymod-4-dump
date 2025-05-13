// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_20_2.client.resources.pack.WrappedPackResources;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ anb.class })
public class MixinPack
{
    @Inject(method = { "open" }, at = { @At("RETURN") }, cancellable = true)
    private void labyMod$wrapPackResources(final CallbackInfoReturnable<amh> cir) {
        cir.setReturnValue((Object)new WrappedPackResources((amh)cir.getReturnValue()));
    }
}
