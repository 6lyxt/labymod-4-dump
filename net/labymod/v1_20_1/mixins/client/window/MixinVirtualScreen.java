// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.window;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.version.OpenGLVersionRequester;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fkm.class })
public class MixinVirtualScreen
{
    @Inject(method = { "newWindow" }, at = { @At("HEAD") })
    private void labyMod$applyCustomOpenGLVersion(final eha param0, final String param1, final String param2, final CallbackInfoReturnable<ehn> cir) {
        OpenGLVersionRequester.requestOpenGLVersion();
    }
}
