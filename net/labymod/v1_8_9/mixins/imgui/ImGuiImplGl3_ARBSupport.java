// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.imgui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLVertexArrayObject;
import imgui.gl3.ImGuiImplGl3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { ImGuiImplGl3.class }, remap = false)
public class ImGuiImplGl3_ARBSupport
{
    @Redirect(method = { "createDeviceObjects", "restoreModifiedGlState", "bind" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL32;glBindVertexArray(I)V"))
    private void labyMod$glBindVertexArray(final int id) {
        OpenGLVertexArrayObject.bind(id);
    }
    
    @Redirect(method = { "unbind" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL32;glDeleteVertexArrays(I)V"))
    private void labyMod$glDeleteVertexArrays(final int id) {
        OpenGLVertexArrayObject.delete(id);
    }
    
    @Redirect(method = { "bind" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL32;glGenVertexArrays()I"))
    private int labyMod$glGenVertexArrays() {
        return OpenGLVertexArrayObject.generate();
    }
}
