// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.screen;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bls.class })
public class MixinGuiVideoSettingsResize
{
    @Redirect(method = { "actionPerformed(Lnet/minecraft/client/gui/GuiButton;I)V" }, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$optifine$fixResize(final bls instance, final bib minecraft, final int width, final int height) {
        this.labyMod$resizeParentScreen(minecraft, width, height);
    }
    
    @Redirect(method = { "mouseClicked", "mouseReleased" }, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$fixResize(final bls instance, final bib minecraft, final int width, final int height) {
        this.labyMod$resizeParentScreen(minecraft, width, height);
    }
    
    @Unique
    private void labyMod$resizeParentScreen(final bib minecraft, final int width, final int height) {
        final blk screen = minecraft.m;
        if (screen == null) {
            return;
        }
        screen.a(minecraft, width, height);
    }
}
