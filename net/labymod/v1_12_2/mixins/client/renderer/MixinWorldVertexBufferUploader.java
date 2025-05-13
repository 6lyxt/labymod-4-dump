// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_12_2.client.render.vertex.buffer.CustomRenderer;
import net.labymod.v1_12_2.client.render.vertex.CustomVertexFormat;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bul.class })
public class MixinWorldVertexBufferUploader
{
    @Inject(method = { "draw(Lnet/minecraft/client/renderer/BufferBuilder;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$useCustomUploader(final buk renderer, final CallbackInfo ci) {
        Laby.gfx().invalidateBuffers();
        final cea format = renderer.g();
        if (format == null) {
            return;
        }
        if (((CustomVertexFormat)format).isCustom()) {
            CustomRenderer.draw(renderer);
            ci.cancel();
        }
    }
}
