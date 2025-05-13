// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.render.vertex.buffer.CustomRenderer;
import net.labymod.v1_8_9.client.render.vertex.CustomVertexFormat;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfe.class })
public class MixinWorldVertexBufferUploader
{
    @Inject(method = { "draw(Lnet/minecraft/client/renderer/WorldRenderer;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$useCustomUploader(final bfd renderer, final CallbackInfo ci) {
        Laby.gfx().invalidateBuffers();
        final bmu format = renderer.g();
        if (format == null) {
            return;
        }
        if (((CustomVertexFormat)format).isCustom()) {
            CustomRenderer.draw(renderer);
            ci.cancel();
        }
    }
    
    @Insert(method = { "draw(Lnet/minecraft/client/renderer/WorldRenderer;)V" }, at = @At(remap = false, value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDrawArrays(III)V"))
    private void labyMod$injectProfiler(final bfd renderer, final InsertInfo ci) {
        RenderProfiler.increaseRenderCall();
    }
}
