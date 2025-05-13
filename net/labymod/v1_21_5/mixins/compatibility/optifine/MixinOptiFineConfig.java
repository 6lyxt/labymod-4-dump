// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.compatibility.optifine;

import net.labymod.v1_21_5.client.resources.pack.PackUtil;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin(targets = { "net.optifine.Config" }, remap = false)
public class MixinOptiFineConfig
{
    @Inject(method = { "isFastRender" }, at = { @At("TAIL") }, cancellable = true)
    @Dynamic
    private static void labyMod$disableFastRender(final CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue((Object)false);
    }
    
    @Inject(method = { "isDynamicFov" }, at = { @At("TAIL") }, cancellable = true)
    @Dynamic
    private static void labyMod$fixDynamicFovZoom(final CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue((Object)((boolean)info.getReturnValue() || Laby.references().zoomController().isZoomActive()));
    }
    
    @Inject(method = { "getDefiningResourcePack" }, at = { @At("RETURN") }, cancellable = true)
    @Dynamic
    private static void labyMod$unwrapPackResources(final alr location, final CallbackInfoReturnable<aua> cir) {
        cir.setReturnValue((Object)PackUtil.unwrap((aua)cir.getReturnValue()));
    }
}
