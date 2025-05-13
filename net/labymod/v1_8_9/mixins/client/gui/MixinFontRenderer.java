// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.util.math.vector.Matrix4;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avn.class })
public class MixinFontRenderer
{
    @Shadow
    private boolean k;
    @Shadow
    @Final
    private int[] d;
    @Shadow
    @Final
    private byte[] e;
    private boolean labyMod$translate;
    
    @Inject(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 1, shift = At.Shift.BEFORE) })
    private void labyMod$translateShadowPush(final String text, final float x, final float y, final int color, final boolean shadow, final CallbackInfoReturnable<Integer> ci) {
        final OverlappingTranslator translator = Laby.labyAPI().gfxRenderPipeline().overlappingTranslator();
        if (translator.isTranslated()) {
            this.labyMod$translate = true;
            bfl.E();
            translator.translate(this, VersionedStackProvider.DEFAULT_STACK);
        }
    }
    
    @Inject(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 1, shift = At.Shift.AFTER) })
    private void labyMod$translateShadowPop(final String text, final float x, final float y, final int color, final boolean shadow, final CallbackInfoReturnable<Integer> ci) {
        if (this.labyMod$translate) {
            bfl.F();
            this.labyMod$translate = false;
        }
    }
    
    @Overwrite
    public int a(final char character) {
        if (character == '§') {
            return -1;
        }
        if (character == ' ') {
            return 4;
        }
        final int charIndex = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(character);
        if (character > '\0' && charIndex != -1 && !this.k) {
            return this.d[charIndex];
        }
        if (this.e[character] != 0) {
            final int widthIndex = this.e[character] & 0xFF;
            final int charWidthIndex = widthIndex >>> 4;
            int charWidth = widthIndex & 0xF;
            return (++charWidth - charWidthIndex) / 2 + 1;
        }
        return 0;
    }
}
