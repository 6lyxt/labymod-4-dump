// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.gfx.pipeline.state.CustomGlStateManager;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin("optifine")
@Mixin(targets = { "com.mojang.blaze3d.platform.GlStateManager" })
public class MixinOptiFineGlStateManageShaderProgramState
{
    @Inject(method = { "_glUseProgram" }, at = { @At("TAIL") })
    private static void labyMod$storeShaderProgram(final int program, final CallbackInfo ci) {
        CustomGlStateManager.SHADER_PROGRAM.setProgramId(program);
    }
}
