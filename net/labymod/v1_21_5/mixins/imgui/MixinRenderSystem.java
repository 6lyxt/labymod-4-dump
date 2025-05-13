// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.imgui;

import com.mojang.blaze3d.shaders.ShaderType;
import java.util.function.BiFunction;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.ImGuiPipeline;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderSystem.class })
public class MixinRenderSystem
{
    @Inject(method = { "flipFrame" }, at = { @At("HEAD") })
    private static void labyMod$renderImGui(final long $$0, final fii $$1, final CallbackInfo ci) {
        ImGuiPipeline.getInstance().renderFrame();
    }
    
    @Inject(method = { "initRenderer" }, at = { @At("TAIL") })
    private static void labyMod$initializeImGui(final long windowHandle, final int $$1, final boolean $$2, final BiFunction<alr, ShaderType, String> $$3, final boolean $$4, final CallbackInfo ci) {
        ImGuiPipeline.getInstance().initialize(windowHandle);
    }
}
