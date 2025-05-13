// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bib.class })
public class MixinMinecraftRenderContext
{
    @Inject(method = { "runGameLoop" }, at = { @At("HEAD") })
    private void labyMod$beginFrame(final CallbackInfo ci) {
        this.labyMod$frameContextRegistry().beginFrame();
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At("RETURN") })
    private void labyMod$endFrame(final CallbackInfo ci) {
        this.labyMod$frameContextRegistry().endFrame();
    }
    
    private FrameContextRegistry labyMod$frameContextRegistry() {
        return Laby.labyAPI().gfxRenderPipeline().frameContextRegistry();
    }
}
