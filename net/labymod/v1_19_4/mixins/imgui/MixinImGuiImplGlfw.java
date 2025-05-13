// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.imgui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.glfw.GLFW;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "imgui.glfw.ImGuiImplGlfw$CreateWindowFunction" }, remap = false)
public class MixinImGuiImplGlfw
{
    @Redirect(method = { "accept" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwMakeContextCurrent(J)V"))
    private void labyMod$setIcon(final long window) {
        GLFWUtil.setDebugWindowIcon(window);
        GLFW.glfwMakeContextCurrent(window);
    }
}
