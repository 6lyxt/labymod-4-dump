// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.util.math.vector.Matrix4;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.lwjgl.opengl.GL11;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bip.class })
public class MixinFontRenderer
{
    private boolean labyMod$translate;
    
    @Inject(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 1, shift = At.Shift.BEFORE) })
    private void labyMod$translateShadowPush(final String text, final float x, final float y, final int color, final boolean shadow, final CallbackInfoReturnable<Integer> ci) {
        final OverlappingTranslator translator = Laby.labyAPI().gfxRenderPipeline().overlappingTranslator();
        if (translator.isTranslated()) {
            this.labyMod$translate = true;
            GL11.glPushMatrix();
            translator.translate(this, VersionedStackProvider.DEFAULT_STACK);
        }
    }
    
    @Inject(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 1, shift = At.Shift.AFTER) })
    private void labyMod$translateShadowPop(final String text, final float x, final float y, final int color, final boolean shadow, final CallbackInfoReturnable<Integer> ci) {
        if (this.labyMod$translate) {
            GL11.glPopMatrix();
            this.labyMod$translate = false;
        }
    }
}
