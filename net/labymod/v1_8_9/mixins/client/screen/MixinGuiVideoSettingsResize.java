// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.screen;

import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ayb.class })
public class MixinGuiVideoSettingsResize
{
    @Redirect(method = { "actionPerformed(Lnet/minecraft/client/gui/GuiButton;I)V" }, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$optifine$fixResize(final ayb instance, final ave minecraft, final int width, final int height) {
        this.labyMod$resizeParentScreen(minecraft, width, height);
    }
    
    @Redirect(method = { "mouseClicked", "mouseReleased" }, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$fixResize(final ayb instance, final ave minecraft, final int width, final int height) {
        this.labyMod$resizeParentScreen(minecraft, width, height);
    }
    
    @Unique
    private void labyMod$resizeParentScreen(final ave minecraft, final int width, final int height) {
        final axu screen = minecraft.m;
        if (screen == null) {
            return;
        }
        screen.a(minecraft, width, height);
    }
}
