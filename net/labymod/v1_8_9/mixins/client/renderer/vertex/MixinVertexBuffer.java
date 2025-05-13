// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.vertex;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bmt.class })
public class MixinVertexBuffer
{
    @Insert(method = { "drawArrays(I)V" }, at = @At(remap = false, value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDrawArrays(III)V", shift = At.Shift.AFTER))
    private void labyMod$injectProfiler(final int mode, final InsertInfo ci) {
        RenderProfiler.increaseRenderCall();
    }
}
