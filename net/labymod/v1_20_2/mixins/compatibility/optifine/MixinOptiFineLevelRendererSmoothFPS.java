// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_2.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ foc.class })
public class MixinOptiFineLevelRendererSmoothFPS
{
    @Redirect(method = { "renderLevel" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glFinish()V", remap = false))
    @Dynamic
    private void labyMod$disableSmoothFPS() {
    }
}
