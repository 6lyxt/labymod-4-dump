// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin("optifine")
@Mixin({ eyq.class })
public class MixinOptiFineVideoSettingsScreen
{
    @Inject(method = { "updateGuiScale" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetCursorPos(JDD)V", shift = At.Shift.BEFORE) }, cancellable = true)
    @Dynamic
    private void labyMod$fixMousePosition(final CallbackInfo ci) {
        ci.cancel();
    }
}
