// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.imgui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.ImGuiPipeline;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fey.class })
public class MixinWindow
{
    @Shadow
    @Final
    private long g;
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$initializeImGui(final fez handler, final few screenManager, final fel displayData, final String videoMode, final String title, final CallbackInfo ci) {
        ImGuiPipeline.getInstance().initialize(this.g);
    }
}
