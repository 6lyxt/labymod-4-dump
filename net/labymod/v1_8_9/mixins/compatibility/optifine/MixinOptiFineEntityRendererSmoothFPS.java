// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin("optifine")
@Mixin({ bfk.class })
public final class MixinOptiFineEntityRendererSmoothFPS
{
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glFinish()V", remap = false))
    @Dynamic
    private void labyMod$disableSmoothFPS() {
    }
}
