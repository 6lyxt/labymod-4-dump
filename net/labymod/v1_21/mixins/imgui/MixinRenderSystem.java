// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.imgui;

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
    private static void labyMod$renderImGui(final long windowPointer, final CallbackInfo ci) {
        ImGuiPipeline.getInstance().renderFrame();
    }
}
