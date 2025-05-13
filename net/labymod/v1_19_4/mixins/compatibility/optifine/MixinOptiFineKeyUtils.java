// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_4.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin(targets = { "net.optifine.util.KeyUtils" }, remap = false)
public class MixinOptiFineKeyUtils
{
    @Inject(method = { "fixKeyConflicts" }, at = { @At("HEAD") }, cancellable = true)
    @Dynamic
    private static void labyMod$preventKeyReplacement(final emf[] keys, final emf[] keysPrio, final CallbackInfo ci) {
        ci.cancel();
    }
}
