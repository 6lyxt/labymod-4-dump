// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.skipcolors;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.nio.ByteOrder;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import java.nio.IntBuffer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ buk.class })
public abstract class MixinBufferBuilderSkipColors
{
    @Shadow
    private IntBuffer c;
    
    @Shadow
    protected abstract int c(final int p0);
    
    @Inject(method = { "putColor" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$skipColors(final int color, final int index, final CallbackInfo ci) {
        if (!GlColorAlphaModifier.isModifiedAlpha()) {
            return;
        }
        ci.cancel();
        final int colorIndex = this.c(index);
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final int red = colorFormat.red(color);
        final int green = colorFormat.green(color);
        final int blue = colorFormat.blue(color);
        final int alpha = colorFormat.alpha(color);
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.c.put(colorIndex, ColorFormat.ABGR32.pack(red, green, blue, alpha));
        }
        else {
            this.c.put(colorIndex, red << 24 | green << 16 | blue << 8 | alpha);
        }
    }
}
