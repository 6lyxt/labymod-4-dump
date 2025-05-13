// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.window;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.version.OpenGLVersion;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.version.OpenGLVersionRequester;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eze.class })
public abstract class MixinWindow
{
    @Inject(method = { "<init>" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", shift = At.Shift.BEFORE) })
    private void labyMod$changeOpenGlVersion(final ezf param0, final ezc param1, final eyr param2, final String param3, final String param4, final CallbackInfo ci) {
        OpenGLVersionRequester.consumeOpenGLVersion(version -> {
            GLFW.glfwWindowHint(139266, version.getMajor());
            GLFW.glfwWindowHint(139267, version.getMinor());
        });
    }
    
    @Inject(method = { "setErrorSection" }, at = { @At("TAIL") })
    private void labyMod$firePostStartupInitializeEvent(final String errorSection, final CallbackInfo ci) {
        if (!errorSection.equalsIgnoreCase("Post startup")) {
            return;
        }
        LabyMod.getInstance().eventBus().fire(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_STARTUP));
    }
}
